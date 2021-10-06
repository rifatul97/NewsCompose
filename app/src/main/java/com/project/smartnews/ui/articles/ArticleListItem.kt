package com.project.smartnews.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.size.Scale
import com.project.smartnews.model.ArticleEntity

@Composable
fun ArticleListItem(
    article: ArticleEntity,
    onItemClick: (ArticleEntity) -> Unit
) {

    Card(modifier = Modifier
        .height(100.dp)
        .wrapContentSize()
        .clip(RoundedCornerShape(8.dp))
        .clickable {
            onItemClick
            println("Clicked ${article.title}")
        }
    ) {
        Row(modifier = Modifier.fillMaxWidth()/*.clickable {  }*/, horizontalArrangement = Arrangement.SpaceBetween) {

            Column(
                modifier = Modifier.weight(0.66f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {//, modifier = Modifier.weight(0.66f)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.weight(0.8f)
                )//, modifier = Modifier.padding(start = 8.dp, top = 8.dp))
                //Spacer(Modifier.height(16.dp))
                Text(
                    text = article.sourceName,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.weight(0.2f),
                    textAlign = TextAlign.End
                )//, , modifier = Modifier.padding(start = 8.dp, bottom = 24.dp))
            }

            Image(
                rememberImagePainter(
                    data = article.urlToImage,
                    builder = {
                        size(OriginalSize)
                        scale(Scale.FIT)
                    }
                ),
                contentDescription = "Picture",
                modifier = Modifier.weight(0.34f),
                contentScale = ContentScale.FillHeight
            )
        }
    }

}