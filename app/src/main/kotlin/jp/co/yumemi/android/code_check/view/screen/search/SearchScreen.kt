/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.view.screen.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.co.yumemi.android.code_check.view.component.BaseLayout
import jp.co.yumemi.android.code_check.view.screen.navigate.AppNavController
import jp.co.yumemi.android.code_check.view.util.SearchButton
import jp.co.yumemi.android.code_check.viewmodel.KeepViewModel
import jp.co.yumemi.android.code_check.viewmodel.SearchStatus
import jp.co.yumemi.android.code_check.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    repositoryViewModel: SearchViewModel = viewModel(),
    keepViewModel: KeepViewModel = viewModel(),
    appNav: AppNavController,
) {
    var inputText by rememberSaveable { mutableStateOf("") }
    val searchedResult by repositoryViewModel.searchedRepositoryItems.observeAsState(listOf())
    val searchStatus by repositoryViewModel.searchStatus.observeAsState(SearchStatus.READY)

    fun handleSearch(keyword: String = inputText) {
        try {
            repositoryViewModel.searchResults(keyword)
        } catch (e: Exception) {
            Log.e("", "error", e)
        }
    }

    BaseLayout(
        actions = {
            IconButton(onClick = {appNav.navigateKeep()}){
                Icon(Icons.Filled.Bookmark, contentDescription = "keep")
            }
        }
    ) {
        Column {
            SearchTextInput(
                value = inputText,
                onValueChange = { inputText = it },
                onSearch = { handleSearch() },
                modifier = Modifier.weight(weight = 1f, fill = true),
            )
            SearchMessage(searchStatus, searchedResult)
            SearchResultList(
                keepViewModel = keepViewModel,
                repositoryItems = searchedResult,
                searchStatus = searchStatus,
                gotoRepositoryDetail = { appNav.navigateRepositoryDetail(it) },
                gotoKeepScreen = { appNav.navigateKeep() },
            )
        }
    }

}

