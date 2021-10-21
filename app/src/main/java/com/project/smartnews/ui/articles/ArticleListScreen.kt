package com.project.smartnews.ui.articles

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.project.smartnews.common.ArticleListState
import com.project.smartnews.model.ArticleEntity
import com.project.smartnews.model.NewsCategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun ArticleListScreen(
    navController: NavController,
    articles: ArticleListState,
    isLoading: State<Boolean>,
    categories: List<NewsCategoryEntity>,
    articleListViewModel: ArticleListViewModel
) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    val swipeRefreshState = rememberSwipeRefreshState(isLoading.value)
    var currentSelectedCategory by remember { mutableStateOf("Top Heading")}
    var mList: HashMap<String, List<ArticleEntity>> by remember {  mutableStateOf (hashMapOf()) }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            CoroutineScope(Dispatchers.IO).launch {
                mList.clear()
                articleListViewModel.forceRefreshArticles()
                mList = articles.articles
            }
        },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                // Pass the SwipeRefreshState + trigger through
                state = state,
                refreshTriggerDistance = trigger,
                // Enable the scale animation
                scale = true,
                // Change the color and shape
                backgroundColor = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small,
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (isLoading.value) {
                isLoading.value -> {
                    println("isLoading is not false anymore")
                    mList = articles.articles

                    var newsCategorySelected = categories
                        .filter { newsCategoryEntity -> newsCategoryEntity.categorySelected }
                        .map { newsCategoryEntity -> newsCategoryEntity.categoryName }

                    println("showing news - ${newsCategorySelected.toString()}")

                    Column {
                        if (newsCategorySelected.size > 1) {
                            ScrollableTabRow(
                                selectedTabIndex = selectedTabIndex,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                newsCategorySelected
                                    .forEachIndexed { index, categoryName ->
                                        Tab(
                                            selected = selectedTabIndex == index,
                                            onClick = {
                                                selectedTabIndex = index
                                                currentSelectedCategory = categoryName
                                            },
                                            modifier = Modifier.height(50.dp),
                                            text = { Text(categoryName) }
                                        )
                                    }
                            }

                            when (selectedTabIndex) {
                                selectedTabIndex ->
                                    TabContent(
                                        articles = mList[currentSelectedCategory],
                                        navController = navController
                                    )
                            }
                        } else if (newsCategorySelected.size == 1) {
                            TabContent(
                                articles = mList["Top Heading"],
                                navController = navController
                            )
                        } else {
                            //CircularProgressIndicator()
                        }

                    }
                }
                !isLoading.value -> {
                    println("refreshing...")
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
