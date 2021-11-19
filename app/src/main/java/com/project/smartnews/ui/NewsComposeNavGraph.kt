package com.project.smartnews.ui

import android.os.StrictMode
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.project.smartnews.ui.article.ArticleDetailScreen
import com.project.smartnews.ui.article.ArticleDetailViewModel
import com.project.smartnews.ui.articles.ArticleListScreen
import com.project.smartnews.ui.articles.ArticleListViewModel
import com.project.smartnews.ui.bookmarks.BookmarkScreen
import com.project.smartnews.ui.bookmarks.BookmarkViewModel
import com.project.smartnews.ui.search.SearchScreen
import com.project.smartnews.ui.search.SearchScreenViewModel
import com.project.smartnews.ui.user.UserScreen
import com.project.smartnews.ui.user.UserScreenViewModel

@OptIn(ExperimentalPagerApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun NewsComposeNavGraph(
    articleListViewModel: ArticleListViewModel = viewModel(),
    articleDetailViewModel: ArticleDetailViewModel = viewModel(),
    searchScreenViewModel: SearchScreenViewModel = viewModel(),
    userScreenViewModel: UserScreenViewModel = viewModel(),
    bookmarkViewModel: BookmarkViewModel = viewModel(),
    articleListState: LazyListState,
    pagerState: PagerState,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    articleIdClicked: MutableState<Int>
) {
    val bottomNavigationTabs = listOf(Screen.Main, Screen.Search, Screen.Profile, Screen.Bookmark)
    val articles by articleListViewModel.articleListState.observeAsState()
    val categories by articleListViewModel.category.observeAsState(listOf())
    val bookmarks by bookmarkViewModel.bookmarks.observeAsState(listOf())
    var isLoading = articleListViewModel.isLoading.collectAsState()
    val scope = rememberCoroutineScope()

    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
    StrictMode.setThreadPolicy(policy)



    Scaffold(
        bottomBar = {
            if (!currentRoute(navController)?.contains("{articleId}")!!) {
                AppBottomNavigation(navController, bottomNavigationTabs)
            }
        },
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            NavHost(
                navController = navController,
                startDestination = Screen.Main.route
            ) {

                composable(route = Screen.Main.route) {

                    articles?.let { it1 ->
                        ArticleListScreen(
                            navController,
                            it1,
                            isLoading,
                            categories,
                            articleListViewModel
                        )
                    }
                }

                composable(
                    route = Screen.Main.route + "/{articleId}",
                    arguments = listOf(navArgument("articleId") {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    articleIdClicked.value = backStackEntry.arguments?.getInt("articleId") ?: -1
                    println("I clicked ${articleIdClicked.value}")
                    ArticleDetailScreen(
                        articleDetailViewModel,
                        bookmarkViewModel,
                        backStackEntry.arguments?.getInt("articleId")
                    )
                }

                composable(route = Screen.Search.route) {
                    SearchScreen(searchScreenViewModel)
                }

                composable(route = Screen.Profile.route) {
                    UserScreen(articleListViewModel)
                }
                
                composable(route = Screen.Bookmark.route) {
                    BookmarkScreen(bookmarks = bookmarks, bookmarkViewModel)
                }
            }
        }
    }
}

@Composable
fun DetailedTopAppBar(check: () -> Boolean, onChecked: () -> Unit) {
    val liked = remember { mutableStateOf(check.invoke()) }

    TopAppBar(title = { Text("") },
        navigationIcon = {
            IconToggleButton(
                checked = liked.value,
                onCheckedChange = {
                    liked.value = it
                    onChecked.invoke()
                    /*if (liked.value){
                        result.value = "Liked"
                    }else{
                        result.value = "Unliked"
                    }*/
                }
            ) {
                val tint by animateColorAsState(
                    if (liked.value) Color(0xFF7BB661)
                    else Color.LightGray
                )
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    tint = tint
                )
            }

        })
}

@Composable
public fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val index = navBackStackEntry?.destination?.route;
//    println("we live in = $index")
    return "$index"
}
