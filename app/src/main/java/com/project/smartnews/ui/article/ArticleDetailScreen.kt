package com.project.smartnews.ui.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.size.Scale
import com.project.smartnews.model.ArticleEntity
import com.project.smartnews.ui.DetailedTopAppBar
import com.project.smartnews.ui.bookmarks.BookmarkViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun ArticleDetailScreen (
    vm: ArticleDetailViewModel,
    bookmarkViewModel: BookmarkViewModel,
    articleId: Int?
){

   val getArticleDetailById = articleId?.let {
        vm.getArticleById(it).stateIn(CoroutineScope(Dispatchers.IO), Eagerly, null)
    }

    if (getArticleDetailById != null) {
        getArticleDetailById.collectAsState().value?.let {
            val content = vm.scrapeContentFromWeb(it.url, it.sourceName)
            println(it.toString())
            ArticleScreen(id = articleId, article = it, content = content, vm = bookmarkViewModel)
        }
    }
}

@Composable
fun ArticleScreen(id: Int, article: ArticleEntity, content: String, vm: BookmarkViewModel) {
    val articleId = article.id?.toLong()

    Scaffold (
        topBar = {
            if (articleId != null) {
                DetailedTopAppBar(
                    check = { vm.checkIfArticleBookmarked(articleId) },
                    onChecked = {
                        CoroutineScope(Dispatchers.IO).launch {
                            vm.insertBookmark(articleId)
                        }
                    }
                )
            }
        },
        content = {
            Column() {
                Image(
                    rememberImagePainter(
                        data = article.urlToImage,
                        builder = {
                            size(OriginalSize)
                            scale(Scale.FIT)
                        }
                    ),
                    contentDescription = "Picture",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillHeight
                )
                println(article.id)
                Text(text = article.title, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = content)
            }
        }
    )
}