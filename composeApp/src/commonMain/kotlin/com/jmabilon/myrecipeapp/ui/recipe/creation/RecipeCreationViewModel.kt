package com.jmabilon.myrecipeapp.ui.recipe.creation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jmabilon.myrecipeapp.domain.ai.repository.AiRepository
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientSectionDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDifficulty
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeIngredientDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeSourceType
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import com.jmabilon.myrecipeapp.domain.recipe.usecase.CreateRecipeUseCase
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationAction
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationEvent
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationState
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationSteps
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class RecipeCreationViewModel(
    savedStateHandle: SavedStateHandle,
    private val createRecipeUseCase: CreateRecipeUseCase,
    private val aiRepository: AiRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<RecipeCreationRoute>()

    private val _event = MutableSharedFlow<RecipeCreationEvent>()
    val event = _event.asSharedFlow()

    private val pendingFinalRecipe = MutableStateFlow(createEmptyRecipe())

    private val _state = MutableStateFlow(RecipeCreationState())
    val state = _state
        .onStart {
            loadData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = RecipeCreationState()
        )

    fun onAction(action: RecipeCreationAction) {
        when (action) {
            RecipeCreationAction.OnPreviousStepClick -> goToPreviousStep()

            // First Step
            is RecipeCreationAction.OnRecipeTitleChange -> {
                _state.update { it.copy(recipeTitle = action.title) }
            }

            is RecipeCreationAction.OnRecipeImagePicked -> {
                action.imageBytes?.let { imageBytes ->
                    _state.update {
                        it.copy(
                            recipeImage = imageBytes.toImmutableList()
                        )
                    }
                }
            }

            is RecipeCreationAction.OnValidateFirstStep -> validateFirstStep()

            // Second Step
            is RecipeCreationAction.OnAddIngredientClick -> addIngredientToGroup(
                action.groupId,
                action.ingredientName,
                action.ingredientQuantity.toDoubleOrNull(),
                action.ingredientUnit
            )

            is RecipeCreationAction.OnAddIngredientGroupClick -> addIngredientGroup(action.groupName)
            is RecipeCreationAction.OnRemoveIngredientClick -> removeIngredient(
                action.ingredientId,
                action.groupId
            )

            RecipeCreationAction.OnValidateSecondStep -> validateSecondStep()
            is RecipeCreationAction.OnAddRecipeStepClick -> addRecipeStepClick(action.stepDescription)

            is RecipeCreationAction.OnRemoveRecipeStepClick -> removeRecipeStepClick(action.stepId)
            RecipeCreationAction.OnValidateThirdStep -> validateThirdStep()
        }
    }

    private fun loadData() {
        if (!args.fromAiAnalyzer) return

        viewModelScope.launch {
            aiRepository.getTempRecipe()?.let { recipe ->
                pendingFinalRecipe.update { recipe }

                _state.update {
                    it.copy(
                        currentStep = RecipeCreationSteps.FirstStep,
                        recipeTitle = recipe.title,
                        ingredientGroups = recipe.ingredientSections,
                        steps = recipe.steps
                    )
                }
            }
        }
    }

    private fun goToPreviousStep() {
        viewModelScope.launch {
            val currentStep = _state.value.currentStep

            _state.update { it.copy(currentStep = currentStep.previousStep()) }
        }
    }

    // First Step

    private fun validateFirstStep() {
        viewModelScope.launch {
            val title = _state.value.recipeTitle.trim()

            if (title.isEmpty()) return@launch

            pendingFinalRecipe.update { it.copy(title = title) }
            _state.update { it.copy(currentStep = RecipeCreationSteps.SecondStep) }
        }
    }

    // Second Step

    private fun addIngredientToGroup(
        groupId: String,
        ingredientName: String,
        ingredientQuantity: Double?,
        ingredientUnit: String
    ) {
        viewModelScope.launch {
            val newIngredientOrder = (state.value.ingredientGroups
                .firstOrNull { it.id == groupId }?.ingredients?.size ?: return@launch) + 1
            val newIngredient = createEmptyIngredient(
                groupId = groupId,
                name = ingredientName,
                quantity = ingredientQuantity,
                unit = ingredientUnit.ifBlank { null },
                note = null,
                sortOrder = newIngredientOrder
            )

            val newIngredientGroups = state.value.ingredientGroups.map { group ->
                if (group.id == groupId) {
                    group.copy(ingredients = group.ingredients + newIngredient)
                } else {
                    group
                }
            }

            _state.update { it.copy(ingredientGroups = newIngredientGroups) }
        }
    }

    private fun removeIngredient(ingredientId: String, groupId: String) {
        viewModelScope.launch {
            val newIngredientGroups = state.value.ingredientGroups.map { group ->
                if (group.id == groupId) {
                    group.copy(ingredients = group.ingredients.filterNot { it.id == ingredientId })
                } else {
                    group
                }
            }

            _state.update { it.copy(ingredientGroups = newIngredientGroups) }
        }
    }

    private fun addIngredientGroup(groupName: String) {
        viewModelScope.launch {
            val newGroup = createEmptyIngredientGroup(
                groupName = groupName,
                order = _state.value.ingredientGroups.size + 1
            )

            _state.update {
                it.copy(
                    ingredientGroups = it.ingredientGroups + newGroup
                )
            }
        }
    }

    private fun validateSecondStep() {
        viewModelScope.launch {
            val currentIngredientGroups = _state.value.ingredientGroups.ifEmpty { return@launch }

            pendingFinalRecipe.update { it.copy(ingredientSections = currentIngredientGroups) }
            _state.update { it.copy(currentStep = RecipeCreationSteps.ThirdStep) }
        }
    }

    // Third Step

    private fun addRecipeStepClick(
        stepDescription: String
    ) {
        val newStep = createEmptyRecipeStep(
            description = stepDescription,
            order = _state.value.steps.size + 1
        )

        _state.update {
            it.copy(
                steps = it.steps + newStep
            )
        }
    }

    private fun removeRecipeStepClick(stepId: String) {
        val newSteps = state.value.steps.filterNot { it.id == stepId }

        _state.update {
            it.copy(
                steps = newSteps
            )
        }
    }

    private fun validateThirdStep() {
        viewModelScope.launch {
            val currentSteps = _state.value.steps.ifEmpty { return@launch }

            pendingFinalRecipe.update { it.copy(steps = currentSteps) }

            createRecipeUseCase(
                recipe = pendingFinalRecipe.value,
                image = _state.value.recipeImage?.toByteArray()
            )
                .onSuccess {
                    _event.emit(RecipeCreationEvent.OnRecipeCreatedSuccessfully)
                }
                .onFailure {
                    print("Failed to create recipe: ${it.message}")
                }
        }
    }

    // Utils

    private fun createEmptyRecipe(): RecipeDomain = RecipeDomain(
        id = Uuid.random().toString(),
        title = "",
        photoUrl = null,
        ingredientSections = emptyList(),
        steps = emptyList(),
        sourceUrl = null,
        sourceType = RecipeSourceType.Manual,
        prepTimeSeconds = 0,
        servingsBase = 1,
        difficulty = RecipeDifficulty.Unknown
    )

    private fun createEmptyIngredientGroup(groupName: String, order: Int): IngredientSectionDomain =
        IngredientSectionDomain(
            id = Uuid.random().toString(),
            recipeId = pendingFinalRecipe.value.id,
            name = groupName,
            ingredients = emptyList(),
            sortOrder = order
        )

    private fun createEmptyIngredient(
        groupId: String,
        name: String,
        quantity: Double?,
        unit: String?,
        note: String?,
        sortOrder: Int
    ): RecipeIngredientDomain = RecipeIngredientDomain(
        id = Uuid.random().toString(),
        sectionId = groupId,
        name = name,
        quantity = quantity,
        unit = unit,
        note = note,
        sortOrder = sortOrder
    )

    private fun createEmptyRecipeStep(
        description: String,
        order: Int
    ): RecipeStepDomain = RecipeStepDomain(
        id = Uuid.random().toString(),
        recipeId = pendingFinalRecipe.value.id,
        instructions = description,
        sortOrder = order
    )
}
