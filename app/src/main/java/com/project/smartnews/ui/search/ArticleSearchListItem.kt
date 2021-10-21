package com.project.smartnews.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.size.Scale
import com.project.smartnews.model.ArticleEntity
import com.project.smartnews.network.ArticleResponse

@Composable
fun ArticleSearchListItem(
    article: ArticleResponse
) {
    val uriHandler = LocalUriHandler.current

    Card(modifier = Modifier
        .height(100.dp)
        .wrapContentSize()
        .clip(RoundedCornerShape(8.dp))
        .clickable {
            uriHandler.openUri(article.url)
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
                )

                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.weight(0.2f),
                    textAlign = TextAlign.End
                )
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