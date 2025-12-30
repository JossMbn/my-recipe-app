package com.jmabilon.myrecipeapp.ui.ai

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmabilon.myrecipeapp.core.prensentation.designsystem.picker.rememberImagePicker
import com.jmabilon.myrecipeapp.core.prensentation.designsystem.picker.toComposeImageBitmap
import com.jmabilon.myrecipeapp.core.prensentation.extension.collectAsEvents
import com.jmabilon.myrecipeapp.ui.ai.model.AiAnalyzerAction
import com.jmabilon.myrecipeapp.ui.ai.model.AiAnalyzerContentState
import com.jmabilon.myrecipeapp.ui.ai.model.AiAnalyzerEvent
import com.jmabilon.myrecipeapp.ui.ai.model.AiAnalyzerState
import myrecipeapp.composeapp.generated.resources.Res
import myrecipeapp.composeapp.generated.resources.ic_arrow_left_alt_rounded
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AiAnalyzerRoot(
    viewModel: AiAnalyzerViewModel = koinViewModel(),
    navigator: AiAnalyzerNavigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.event.collectAsEvents { event ->
        when (event) {
            is AiAnalyzerEvent.SuccessAnalyzeRecipe -> {
                navigator.navigateToRecipeDetails()
            }
        }
    }

    AiAnalyzerPage(
        state = state,
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AiAnalyzerPage(
    state: AiAnalyzerState,
    onAction: (AiAnalyzerAction) -> Unit,
    navigator: AiAnalyzerNavigator
) {
    val imagePicker = rememberImagePicker(
        onImagePicked = {
            onAction(AiAnalyzerAction.OnRecipeImagePicked(it))
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Analyze Recipe Image",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
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
                }
            )
        },
        bottomBar = {
            if (state.contentState == AiAnalyzerContentState.ImagePickingContent) {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { imagePicker.pickImage() }) {
                        Text("Pick Image")
                    }

                    if (state.recipeImage != null) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onAction(AiAnalyzerAction.OnAnalyzeImageClicked)
                            }
                        ) {
                            Text("Analyze Image")
                        }
                    }
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        when (state.contentState) {
            AiAnalyzerContentState.ImagePickingContent -> {
                AiAnalyzerPageContent(
                    modifier = Modifier.padding(innerPadding),
                    state = state,
                    onAction = onAction,
                    navigator = navigator
                )
            }

            AiAnalyzerContentState.AnalyzingContent -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun AiAnalyzerPageContent(
    modifier: Modifier = Modifier,
    state: AiAnalyzerState,
    onAction: (AiAnalyzerAction) -> Unit,
    navigator: AiAnalyzerNavigator
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (state.recipeImage != null) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = state.recipeImage.toComposeImageBitmap(),
                contentDescription = "Recipe Image",
            )
        } else {
            Text("No image selected")
        }
    }
}

@Preview
@Composable
private fun AiAnalyzerPagePreview() {
    MaterialTheme {
        AiAnalyzerPage(
            state = AiAnalyzerState(),
            onAction = { /* no-op */ },
            navigator = AiAnalyzerNavigatorImpl()
        )
    }
}
