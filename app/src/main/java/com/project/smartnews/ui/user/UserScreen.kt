package com.project.smartnews.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.unit.dp
import com.project.smartnews.model.NewsCategoryEntity
import com.project.smartnews.ui.articles.ArticleListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun UserScreen(
    //userScreenViewModel: UserScreenViewModel
    articleListViewModel: ArticleListViewModel
) {
    val mList = remember { mutableStateListOf<NewsCategoryEntity>() }
    mList.swapList(articleListViewModel.getAllNewsCategory() ?: emptyList())

    // contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        itemsIndexed(mList) { index, category ->
            val categoryName = category.categoryName
            if (categoryName != "Top Heading") {
                CategoryListItem(categoryName = category.categoryName,
                    check = { articleListViewModel.getUserNewsCategorySelected(categoryName) },
                    onChecked = {
                        CoroutineScope(Dispatchers.IO).launch {
                            articleListViewModel.updateSelection(category.categoryName)

                        }
                        articleListViewModel.getAllNewsCategory()
                    },
                    { articleListViewModel.loadCategory() })
            }


        }
    }

}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}