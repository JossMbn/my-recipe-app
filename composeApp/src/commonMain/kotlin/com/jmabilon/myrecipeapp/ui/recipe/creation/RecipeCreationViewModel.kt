package com.jmabilon.myrecipeapp.ui.recipe.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.IngredientDomain
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.IngredientGroupDomain
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeStepDomain
import com.jmabilon.myrecipeapp.domain.authentication.repository.RecipeRepository
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationAction
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationEvent
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationState
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationSteps
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
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<RecipeCreationEvent>()
    val event = _event.asSharedFlow()

    private val pendingFinalRecipe = MutableStateFlow(createEmptyRecipe())

    private val _state = MutableStateFlow(RecipeCreationState())
    val state = _state
        .onStart {
            // Load initial data here
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

            is RecipeCreationAction.OnValidateFirstStep -> validateFirstStep()

            // Second Step
            is RecipeCreationAction.OnAddIngredientClick -> addIngredientToGroup(
                action.groupId,
                action.ingredientName,
                action.ingredientQuantity,
                action.ingredientUnit
            )

            is RecipeCreationAction.OnAddIngredientGroupClick -> addIngredientGroup(action.groupName)
            is RecipeCreationAction.OnRemoveIngredientClick -> removeIngredient(
                action.ingredientId,
                action.groupId
            )

            RecipeCreationAction.OnValidateSecondStep -> validateSecondStep()
            is RecipeCreationAction.OnAddRecipeStepClick -> addRecipeStepClick(
                action.stepDescription,
                action.durationInMinutes
            )

            is RecipeCreationAction.OnRemoveRecipeStepClick -> removeRecipeStepClick(action.stepId)
            RecipeCreationAction.OnValidateThirdStep -> validateThirdStep()
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
        ingredientQuantity: String,
        ingredientUnit: String
    ) {
        viewModelScope.launch {
            val newIngredientOrder = (state.value.ingredientGroups
                .firstOrNull { it.id == groupId }?.ingredients?.size ?: return@launch) + 1
            val newIngredient = createEmptyIngredient(
                groupId = groupId,
                name = ingredientName,
                quantity = ingredientQuantity.ifBlank { null },
                unit = ingredientUnit.ifBlank { null },
                order = newIngredientOrder
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

            pendingFinalRecipe.update { it.copy(ingredientGroups = currentIngredientGroups) }
            _state.update { it.copy(currentStep = RecipeCreationSteps.ThirdStep) }
        }
    }

    // Third Step

    private fun addRecipeStepClick(
        stepDescription: String,
        durationInMinutes: String?
    ) {
        val durationInMinutes = durationInMinutes?.toIntOrNull()
        val newStep = createEmptyRecipeStep(
            description = stepDescription,
            durationInMinutes = durationInMinutes,
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

            recipeRepository.createRecipe(pendingFinalRecipe.value)
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
        ingredientGroups = emptyList(),
        steps = emptyList()
    )

    private fun createEmptyIngredientGroup(groupName: String, order: Int): IngredientGroupDomain =
        IngredientGroupDomain(
            id = Uuid.random().toString(),
            recipeId = pendingFinalRecipe.value.id,
            name = groupName,
            ingredients = emptyList(),
            order = order
        )

    private fun createEmptyIngredient(
        groupId: String,
        name: String,
        quantity: String?,
        unit: String?,
        order: Int
    ): IngredientDomain = IngredientDomain(
        id = Uuid.random().toString(),
        groupId = groupId,
        name = name,
        quantity = quantity,
        unit = unit,
        order = order
    )

    private fun createEmptyRecipeStep(
        description: String,
        durationInMinutes: Int?,
        order: Int
    ): RecipeStepDomain = RecipeStepDomain(
        id = Uuid.random().toString(),
        recipeId = pendingFinalRecipe.value.id,
        description = description,
        durationMinutes = durationInMinutes,
        order = order
    )
}
