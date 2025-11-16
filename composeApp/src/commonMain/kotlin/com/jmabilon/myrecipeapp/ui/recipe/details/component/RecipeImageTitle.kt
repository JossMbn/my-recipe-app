package com.jmabilon.myrecipeapp.ui.recipe.details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import myrecipeapp.composeapp.generated.resources.Res
import myrecipeapp.composeapp.generated.resources.recipe_placeholder
import org.jetbrains.compose.resources.painterResource

@Composable
fun RecipeImageTitle(
    imageUrl: String,
    title: String
) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            error = painterResource(Res.drawable.recipe_placeholder),
            fallback = painterResource(Res.drawable.recipe_placeholder)
        )

        Box(
            modifier = Modifier
                .height(60.dp)
                .offset(y = 30.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .dropShadow(
                    shape = RoundedCornerShape(10.dp),
                    shadow = Shadow(
                        radius = 8.dp,
                        spread = 0.dp,
                        offset = DpOffset(0.dp, 4.dp),
                        color = Color(0x26000000)
                    )
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }
    }
}
