package com.project.smartnews.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.project.smartnews.network.ArticleResponse

@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel
) {
    var text by remember { mutableStateOf("") }
    var show by remember { mutableStateOf(false) }

    //Modifier.padding(16.dp)
    Column() {
        
        Row() {
            TextField(
                value = text,
                onValueChange = { text = it
                                if (text.length < 4) {
                                    show = false
                                }},
                label = { Text("Look up Articles by text") },
                modifier = Modifier.weight(0.80f),
                singleLine = true,
                trailingIcon = {
                    if (text.length >= 4) {
                        IconButton(onClick = {
                            show = text.length >= 4
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
            /*
            Button(onClick = {
                show = text.length >= 4
            },
                modifier = Modifier.weight(0.20f)
             ) {}

             */
        }

        if(show) {
            ArticleSearchList(searchScreenViewModel.getArticlesByText(text))
        }
        
    }

}

@Composable
fun ArticleSearchList(articles: List<ArticleResponse>){

    if (articles.isNotEmpty()) {
        LazyColumn {
            items(articles) { article ->
                ArticleSearchListItem(article = article)
            }
        }
    } else {
        Text("No articles found!")
    }

}