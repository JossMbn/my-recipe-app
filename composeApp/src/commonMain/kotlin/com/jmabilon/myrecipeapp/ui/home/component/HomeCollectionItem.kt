package com.jmabilon.myrecipeapp.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun HomeCollectionItem(
    modifier: Modifier = Modifier,
    firstUrl: String?,
    secondUrl: String?,
    thirdUrl: String?,
    collectionName: String,
    recipeCount: Int
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .width(160.dp)
                .height(180.dp)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .weight(0.5f)
                        .clip(RoundedCornerShape(4.dp)),
                    model = firstUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.weight(0.5f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .weight(0.5f)
                            .clip(RoundedCornerShape(4.dp)),
                        model = secondUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    AsyncImage(
                        modifier = Modifier
                            .weight(0.5f)
                            .clip(RoundedCornerShape(4.dp)),
                        model = thirdUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Text(
                text = "$collectionName ($recipeCount recipes)",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
