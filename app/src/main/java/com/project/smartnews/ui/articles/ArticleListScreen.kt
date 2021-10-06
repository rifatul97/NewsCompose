package com.project.smartnews.ui.articles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.map
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.smartnews.ui.ArticleListItem

@Composable
fun ArticleListScreen(
    viewModel: ArticleListViewModel = viewModel(),
    articleListState: LazyListState
) {
    println("ArticleListScreen initiated")

    val articleListState = viewModel.getArticles.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(articleListState.value) { article ->
                    ArticleListItem(
                        article = article,
                        onItemClick = {
                            //navController.navigate(Screen.ArticleDetailScreen.route + "/${article.id}")
                        })
                    Spacer(modifier = Modifier.height(5.dp))
            }
        }

    }
}