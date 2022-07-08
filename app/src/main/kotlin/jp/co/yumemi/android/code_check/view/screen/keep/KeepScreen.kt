package jp.co.yumemi.android.code_check.view.screen.keep

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.RepositoryItem
import jp.co.yumemi.android.code_check.view.component.BaseLayout
import jp.co.yumemi.android.code_check.view.screen.navigate.AppNavController
import jp.co.yumemi.android.code_check.view.util.ListItem
import jp.co.yumemi.android.code_check.view.util.SearchButton
import jp.co.yumemi.android.code_check.viewmodel.KeepViewModel


@Composable
fun KeepScreen(
    appNav: AppNavController,
    keepViewModel: KeepViewModel = viewModel(),
) {
    val keeps by keepViewModel.keeps.observeAsState(mutableSetOf())
    Log.d("keepScreen","${keeps.size}")
    LaunchedEffect(true){
        keepViewModel.load()
    }
    BaseLayout(
        title = stringResource(R.string.keep_title),
        actions = {
            SearchButton(onClick = { appNav.navigateSearch() })
        }
    ) {
        Column {
            keeps.forEach {
                KeepListItem(it)
            }
            if (keeps.isEmpty()) {
                ListItem(text = stringResource(R.string.none_keep))
            }
            ListItem(text = stringResource(R.string.back), onClick = {
                appNav.navigateUp()
            })
        }
    }
}

@Composable
fun KeepListItem(keep: RepositoryItem) {
    ListItem(text = "${keep.name}")
}