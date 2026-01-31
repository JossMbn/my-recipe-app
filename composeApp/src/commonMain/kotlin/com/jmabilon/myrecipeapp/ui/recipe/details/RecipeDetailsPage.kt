package com.jmabilon.myrecipeapp.ui.recipe.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmabilon.myrecipeapp.domain.recipe.model.IngredientSectionDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDifficulty
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeIngredientDomain
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeSourceType
import com.jmabilon.myrecipeapp.domain.recipe.model.RecipeStepDomain
import com.jmabilon.myrecipeapp.ui.recipe.details.component.ExpandableRecipeContainer
import com.jmabilon.myrecipeapp.ui.recipe.details.component.RecipeImageTitle
import com.jmabilon.myrecipeapp.ui.recipe.details.model.RecipeDetailsAction
import com.jmabilon.myrecipeapp.ui.recipe.details.model.RecipeDetailsState
import com.jmabilon.myrecipeapp.ui.recipe.details.sheet.CheckIngredientsAvailabilitySheet
import myrecipeapp.composeapp.generated.resources.Res
import myrecipeapp.composeapp.generated.resources.ic_arrow_left_alt_rounded
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RecipeDetailsRoot(
    viewModel: RecipeDetailsViewModel = koinViewModel(),
    navigator: RecipeDetailsNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RecipeDetailsPage(
        state = state,
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeDetailsPage(
    state: RecipeDetailsState,
    onAction: (RecipeDetailsAction) -> Unit,
    navigator: RecipeDetailsNavigator
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var isCheckIngredientsSheetOpen by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { /* no-op */ },
                navigationIcon = {
                    IconButton(
                        onClick = navigator::navigateBack
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(Res.drawable.ic_arrow_left_alt_rounded),
                            contentDescription = null
                        )
                    }
                },
                colors = topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.Black
                ),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { isCheckIngredientsSheetOpen = true },
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 40.dp)
                ) {
                    Text(text = "Start cooking")
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        RecipeDetailsPageContent(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            state = state,
            contentPadding = innerPadding,
            onAction = onAction,
            navigator = navigator
        )
    }

    if (isCheckIngredientsSheetOpen) {
        CheckIngredientsAvailabilitySheet(
            ingredientGroups = state.recipe?.ingredientSections.orEmpty(),
            onDismissRequest = { isCheckIngredientsSheetOpen = false }
        )
    }
}

@Composable
private fun RecipeDetailsPageContent(
    modifier: Modifier = Modifier,
    state: RecipeDetailsState,
    contentPadding: PaddingValues,
    onAction: (RecipeDetailsAction) -> Unit,
    navigator: RecipeDetailsNavigator
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
            end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
            bottom = contentPadding.calculateBottomPadding() + 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            RecipeImageTitle(
                imageUrl = state.recipe?.photoUrl.orEmpty(),
                title = state.recipe?.title.orEmpty()
            )
        }

        item {
            ExpandableRecipeContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = "Ingredients",
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    state.recipe?.ingredientSections?.forEach { group ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (group.name.isNotEmpty()) {
                                Text(
                                    text = group.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Black
                                )
                            }

                            Column(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                group.ingredients.forEach { ingredient ->
                                    Text(
                                        text = "• ${ingredient.quantity} ${ingredient.name}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            ExpandableRecipeContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = "Steps",
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    state.recipe?.steps?.forEach { step ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(100.dp))
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(100.dp)
                                    )
                                    .padding(vertical = 4.dp, horizontal = 10.dp),
                                text = "Step ${step.sortOrder}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = step.instructions,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecipeDetailsPagePreview() {
    MaterialTheme {
        RecipeDetailsPage(
            state = RecipeDetailsState(
                recipe = RecipeDomain(
                    id = "1",
                    title = "Delicious Pancakes",
                    photoUrl = null,
                    ingredientSections = listOf(
                        IngredientSectionDomain(
                            id = "group1",
                            recipeId = "1",
                            name = "Batter",
                            sortOrder = 0,
                            ingredients = listOf(
                                RecipeIngredientDomain(
                                    id = "ing1",
                                    name = "All-purpose flour",
                                    quantity = 0.5,
                                    sectionId = "group1",
                                    unit = null,
                                    note = null,
                                    sortOrder = 0,
                                ),
                                RecipeIngredientDomain(
                                    id = "ing2",
                                    name = "Sugar",
                                    quantity = 2.0,
                                    sectionId = "group1",
                                    unit = null,
                                    note = null,
                                    sortOrder = 1
                                ),
                                RecipeIngredientDomain(
                                    id = "ing3",
                                    name = "Baking powder",
                                    quantity = 2.0,
                                    sectionId = "group1",
                                    unit = null,
                                    note = null,
                                    sortOrder = 2
                                )
                            )
                        ),
                        IngredientSectionDomain(
                            id = "group2",
                            recipeId = "1",
                            name = "Toppings",
                            sortOrder = 1,
                            ingredients = listOf(
                                RecipeIngredientDomain(
                                    id = "ing4",
                                    sectionId = "group2",
                                    name = "Maple syrup",
                                    quantity = 2.0,
                                    unit = null,
                                    note = null,
                                    sortOrder = 0
                                ),
                                RecipeIngredientDomain(
                                    id = "ing5",
                                    sectionId = "group2",
                                    name = "Fresh berries",
                                    quantity = 2.0,
                                    unit = null,
                                    note = null,
                                    sortOrder = 1
                                )
                            )
                        )
                    ),
                    steps = listOf(
                        RecipeStepDomain(
                            id = "step1",
                            recipeId = "1",
                            instructions = "In a large bowl, whisk together the flour, sugar, baking powder, baking soda, and salt.",
                            timerSeconds = null,
                            cookTimeSeconds = null,
                            cookTemperature = null,
                            sortOrder = 0
                        ),
                        RecipeStepDomain(
                            id = "step2",
                            recipeId = "1",
                            instructions = "In another bowl, whisk together the buttermilk, egg, and melted butter.",
                            timerSeconds = null,
                            cookTimeSeconds = null,
                            cookTemperature = null,
                            sortOrder = 1
                        )
                    ),
                    sourceUrl = null,
                    sourceType = RecipeSourceType.Manual,
                    prepTimeSeconds = 3600,
                    servingsBase = 4,
                    difficulty = RecipeDifficulty.Medium
                )
            ),
            onAction = { /* no-op */ },
            navigator = RecipeDetailsNavigatorImpl()
        )
    }
}
