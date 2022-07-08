package jp.co.yumemi.android.code_check.view.screen.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.RepositoryItem
import jp.co.yumemi.android.code_check.view.util.ListItem
import jp.co.yumemi.android.code_check.viewmodel.KeepViewModel
import jp.co.yumemi.android.code_check.viewmodel.SearchStatus

/**
 * 検索結果を表示する
 */
@Composable
fun SearchResultList(
    keepViewModel: KeepViewModel = viewModel(),
    repositoryItems: List<RepositoryItem>,
    searchStatus: SearchStatus,
    gotoRepositoryDetail: (RepositoryItem) -> Unit,
    gotoKeepScreen: () -> Unit,
) {
    when (searchStatus) {
        SearchStatus.SEARCHING ->
            //検索中はインジケーターを表示
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
            }
        SearchStatus.SUCCESS ->
            //検索成功時は検索結果一覧を表示
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                val keeps = keepViewModel.keeps.observeAsState(mutableSetOf())
                Log.d("","repositoryItems >>>")
                if (repositoryItems.isNotEmpty()) {
                    //検索結果が1件以上あるとき
                    repositoryItems.forEach { repo ->
                        SearchResultListItem(
                            repo = repo,
                            onClick = { gotoRepositoryDetail(repo) },
                            isKeep = keeps?.value?.contains(repo) ?: false,
                            onChangeKeep = { keepViewModel.toggleKeep(repo) },
                        )
                    }
                } else {
                    //検索結果が0件の時
                    ListItem(text = stringResource(R.string.nothing_search_result))
                }
            }
        else ->
            //初期状態やエラー時はメニューを表示する
            Column {
                val menuTitle = stringResource(R.string.menu)
                val gotoKeepMsg = stringResource(R.string.menu_goto_keep)

                ListItem(text = menuTitle)
                ListItem(
                    text = gotoKeepMsg,
                    onClick = { gotoKeepScreen() }
                )
            }
    }
}

val keepItemIcon = Icons.Filled.Bookmark
val notKeepItemIcon = Icons.Rounded.BookmarkBorder

/**
 * 検索結果の各行
 */
@Composable
fun SearchResultListItem(
    repo: RepositoryItem,
    isKeep: Boolean,
    onChangeKeep: (Boolean) -> Unit,
    onClick: () -> Unit = {},
) {
    Icons.Rounded
    Icons.Sharp
    Icons.TwoTone
    ListItem(
        text = repo.name,
        onClick = onClick,
        icon = {
            IconButton(onClick = { onChangeKeep(!isKeep) }) {
                Icon(
                    if (isKeep)
                        keepItemIcon
                    else
                        notKeepItemIcon,
                    contentDescription = "keep",
                )
            }
        }
    )
}

