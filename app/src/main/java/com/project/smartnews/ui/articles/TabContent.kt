package com.project.smartnews.ui.articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.smartnews.model.ArticleEntity

@Composable
fun TabContent(
    articles: List<ArticleEntity>?,
    navController: NavController
) {
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
            items(articles ?: emptyList()) { article ->
                ArticleListItem(
                    article = article,
                    onItemClick = {
                        navController.navigate("main/${article.id}")
                    })
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

    }

}

