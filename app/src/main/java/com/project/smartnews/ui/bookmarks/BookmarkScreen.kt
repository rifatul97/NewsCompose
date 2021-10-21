package com.project.smartnews.ui.bookmarks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.project.smartnews.common.toEntity
import com.project.smartnews.model.ArticleBookmarkEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkScreen(bookmarks: List<ArticleBookmarkEntity>, vm: BookmarkViewModel) {
    val expanded = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Bookmark")
                },

                actions = {
                    Box(
                        Modifier
                            .wrapContentSize(Alignment.TopEnd)
                    ) {
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                        ) {
                            DropdownMenuItem(onClick = {
                                expanded.value = false
                            }) {
                                Text("Clear All Bookmarks")
                            }
                        }
                    }
                },
                backgroundColor = Color(0xFDCD7F32),
                elevation = AppBarDefaults.TopAppBarElevation
            )
        },
    ) {
        LazyColumn {
            itemsIndexed(bookmarks) { index, article ->
                val articleEntity = article.toEntity()
                ArticleBookmarkListItem(article)
            }
        }
    }

}

