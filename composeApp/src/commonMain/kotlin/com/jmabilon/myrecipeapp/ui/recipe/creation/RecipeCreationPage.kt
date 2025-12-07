package com.jmabilon.myrecipeapp.ui.recipe.creation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmabilon.myrecipeapp.core.prensentation.extension.clearFocusOnTap
import com.jmabilon.myrecipeapp.core.prensentation.extension.collectAsEvents
import com.jmabilon.myrecipeapp.ui.recipe.creation.components.steps.RecipeCreationFirstStepPage
import com.jmabilon.myrecipeapp.ui.recipe.creation.components.steps.RecipeCreationSecondStepPage
import com.jmabilon.myrecipeapp.ui.recipe.creation.components.steps.RecipeCreationThirdStepPage
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationAction
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationEvent
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationState
import com.jmabilon.myrecipeapp.ui.recipe.creation.model.RecipeCreationSteps
import com.jmabilon.myrecipeapp.ui.recipe.creation.sheet.GroupCreationSheet
import com.jmabilon.myrecipeapp.ui.recipe.creation.sheet.IngredientCreationSheet
import com.jmabilon.myrecipeapp.ui.recipe.creation.sheet.StepCreationSheet
import myrecipeapp.composeapp.generated.resources.Res
import myrecipeapp.composeapp.generated.resources.ic_arrow_left_alt_rounded
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RecipeCreationRoot(
    viewModel: RecipeCreationViewModel = koinViewModel(),
    navigator: RecipeCreationNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.event.collectAsEvents { event ->
        when (event) {
            is RecipeCreationEvent.OnRecipeCreatedSuccessfully -> {
                navigator.navigateBack()
            }
        }
    }

    RecipeCreationPage(
        state = state,
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeCreationPage(
    state: RecipeCreationState,
    onAction: (RecipeCreationAction) -> Unit,
    navigator: RecipeCreationNavigator
) {
    val headerTitle = remember(state.currentStep) {
        when (state.currentStep) {
            RecipeCreationSteps.FirstStep -> "Step 1/3"
            RecipeCreationSteps.SecondStep -> "Step 2/3"
            RecipeCreationSteps.ThirdStep -> "Step 3/3"
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .clearFocusOnTap(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = headerTitle,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (state.currentStep != RecipeCreationSteps.FirstStep) {
                                onAction(RecipeCreationAction.OnPreviousStepClick)
                            } else {
                                navigator.navigateBack()
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(Res.drawable.ic_arrow_left_alt_rounded),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        when (state.currentStep) {
                            RecipeCreationSteps.FirstStep -> {
                                onAction(RecipeCreationAction.OnValidateFirstStep)
                            }

                            RecipeCreationSteps.SecondStep -> {
                                onAction(RecipeCreationAction.OnValidateSecondStep)
                            }

                            RecipeCreationSteps.ThirdStep -> {
                                onAction(RecipeCreationAction.OnValidateThirdStep)
                            }
                        }
                    },
                ) {
                    Text(text = "Next")
                }
            }
        }
    ) { innerPadding ->
        RecipeCreationPageContent(
            modifier = Modifier
                .padding(innerPadding),
            state = state,
            onAction = onAction,
            navigator = navigator
        )
    }
}

@Composable
private fun RecipeCreationPageContent(
    modifier: Modifier = Modifier,
    state: RecipeCreationState,
    onAction: (RecipeCreationAction) -> Unit,
    navigator: RecipeCreationNavigator
) {
    var isGroupCreationSheetVisible by remember { mutableStateOf(false) }
    var pendingGroupId by remember { mutableStateOf<String?>(null) }
    var isIngredientCreationSheetVisible by remember { mutableStateOf(false) }
    var isStepCreationSheetVisible by remember { mutableStateOf(false) }

    when (state.currentStep) {
        RecipeCreationSteps.FirstStep -> RecipeCreationFirstStepPage(
            modifier = modifier,
            title = state.recipeTitle,
            recipeImage = state.recipeImage,
            onValueChange = { onAction(RecipeCreationAction.OnRecipeTitleChange(title = it)) },
            onImagePicked = { byteArray ->
                onAction(RecipeCreationAction.OnRecipeImagePicked(imageBytes = byteArray))
            }
        )

        RecipeCreationSteps.SecondStep -> RecipeCreationSecondStepPage(
            modifier = modifier,
            ingredientGroups = state.ingredientGroups,
            onAddIngredientsClick = { groupId ->
                pendingGroupId = groupId
                isIngredientCreationSheetVisible = true
            },
            onRemoveIngredientClick = { ingredientId, groupId ->
                onAction(
                    RecipeCreationAction.OnRemoveIngredientClick(
                        ingredientId = ingredientId,
                        groupId = groupId
                    )
                )
            },
            onAddIngredientGroupClick = { isGroupCreationSheetVisible = true }
        )

        RecipeCreationSteps.ThirdStep -> RecipeCreationThirdStepPage(
            modifier = modifier,
            steps = state.steps,
            onAddStepClick = { isStepCreationSheetVisible = true },
            onRemoveStepClick = { stepId ->
                onAction(RecipeCreationAction.OnRemoveRecipeStepClick(stepId = stepId))
            },
        )
    }

    if (isGroupCreationSheetVisible) {
        GroupCreationSheet(
            onDismissRequest = { isGroupCreationSheetVisible = false },
            onCreateGroupClick = { groupName ->
                onAction(RecipeCreationAction.OnAddIngredientGroupClick(groupName = groupName))
                isGroupCreationSheetVisible = false
            }
        )
    }

    if (isIngredientCreationSheetVisible) {
        IngredientCreationSheet(
            onDismissRequest = { isIngredientCreationSheetVisible = false },
            onCreateIngredientClick = { name, quantity, unit ->
                pendingGroupId.takeIf { !it.isNullOrEmpty() }?.let { groupId ->
                    onAction(
                        RecipeCreationAction.OnAddIngredientClick(
                            groupId = groupId,
                            ingredientName = name,
                            ingredientQuantity = quantity,
                            ingredientUnit = unit
                        )
                    )
                    pendingGroupId = null
                }
                isIngredientCreationSheetVisible = false
            }
        )
    }

    if (isStepCreationSheetVisible) {
        StepCreationSheet(
            onDismissRequest = { isStepCreationSheetVisible = false },
            onCreateStepClick = { stepInstructions, stepDuration ->
                onAction(
                    RecipeCreationAction.OnAddRecipeStepClick(
                        stepDescription = stepInstructions,
                        durationInMinutes = stepDuration
                    )
                )
                isStepCreationSheetVisible = false
            }
        )
    }
}

@Preview
@Composable
private fun RecipeCreationPagePreview() {
    MaterialTheme {
        RecipeCreationPage(
            state = RecipeCreationState(),
            onAction = { /* no-op */ },
            navigator = RecipeCreationNavigatorImpl()
        )
    }
}
