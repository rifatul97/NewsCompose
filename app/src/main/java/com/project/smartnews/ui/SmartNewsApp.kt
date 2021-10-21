package com.project.smartnews.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.project.smartnews.ui.theme.SmartNewsTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun SmartNewsApp() {
    val articleListState = rememberLazyListState()
    val pagerState = rememberPagerState(pageCount = 6, initialPage = 0)
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    var articleIdClicked = remember { mutableStateOf(0) }


    SmartNewsTheme() {
        SmartNewsNavGraph(
            articleListState = articleListState,
            pagerState = pagerState,
            scaffoldState = scaffoldState,
            navController = navController,
            articleIdClicked = articleIdClicked
        )
    }
}
