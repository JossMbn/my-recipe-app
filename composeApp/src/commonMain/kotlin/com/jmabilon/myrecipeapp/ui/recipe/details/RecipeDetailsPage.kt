package com.jmabilon.myrecipeapp.ui.recipe.details

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.IngredientDomain
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.IngredientGroupDomain
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeDomain
import com.jmabilon.myrecipeapp.domain.authentication.recipe.model.RecipeStepDomain
import com.jmabilon.myrecipeapp.ui.recipe.details.component.ExpandableRecipeContainer
import com.jmabilon.myrecipeapp.ui.recipe.details.component.RecipeImageTitle
import com.jmabilon.myrecipeapp.ui.recipe.details.model.RecipeDetailsAction
import com.jmabilon.myrecipeapp.ui.recipe.details.model.RecipeDetailsState
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
                    state.recipe?.ingredientGroups?.forEach { group ->
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
                                text = "Step ${step.order}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = step.description,
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
                    ingredientGroups = listOf(
                        IngredientGroupDomain(
                            id = "group1",
                            recipeId = "1",
                            name = "Batter",
                            order = 0,
                            ingredients = listOf(
                                IngredientDomain(
                                    id = "ing1",
                                    name = "All-purpose flour",
                                    quantity = "1 1/2 cups",
                                    groupId = "group1",
                                    unit = null,
                                    order = 0,
                                ),
                                IngredientDomain(
                                    id = "ing2",
                                    name = "Sugar",
                                    quantity = "2 tablespoons",
                                    groupId = "group1",
                                    unit = null,
                                    order = 1
                                ),
                                IngredientDomain(
                                    id = "ing3",
                                    name = "Baking powder",
                                    quantity = "2 teaspoons",
                                    groupId = "group1",
                                    unit = null,
                                    order = 2
                                )
                            )
                        ),
                        IngredientGroupDomain(
                            id = "group2",
                            recipeId = "1",
                            name = "Toppings",
                            order = 1,
                            ingredients = listOf(
                                IngredientDomain(
                                    id = "ing4",
                                    name = "Maple syrup",
                                    quantity = "To taste",
                                    groupId = "group2",
                                    unit = null,
                                    order = 0
                                ),
                                IngredientDomain(
                                    id = "ing5",
                                    name = "Fresh berries",
                                    quantity = "To taste",
                                    groupId = "group2",
                                    unit = null,
                                    order = 1
                                )
                            )
                        )
                    ),
                    steps = listOf(
                        RecipeStepDomain(
                            id = "step1",
                            order = 0,
                            description = "In a large bowl, whisk together the flour, sugar, baking powder, baking soda, and salt.",
                            recipeId = "1",
                            durationMinutes = null
                        ),
                        RecipeStepDomain(
                            id = "step2",
                            order = 1,
                            description = "In another bowl, whisk together the buttermilk, egg, and melted butter.",
                            recipeId = "1",
                            durationMinutes = null
                        )
                    )
                )
            ),
            onAction = { /* no-op */ },
            navigator = RecipeDetailsNavigatorImpl()
        )
    }
}
