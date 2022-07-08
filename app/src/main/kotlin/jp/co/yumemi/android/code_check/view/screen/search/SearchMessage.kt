package jp.co.yumemi.android.code_check.view.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tbsten.memoappcompose.ui.theme.Error
import dev.tbsten.memoappcompose.ui.theme.Info
import dev.tbsten.memoappcompose.ui.theme.Success
import jp.co.yumemi.android.code_check.model.RepositoryItem
import jp.co.yumemi.android.code_check.viewmodel.SearchStatus
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import jp.co.yumemi.android.code_check.R

@Composable
fun SearchMessage(searchStatus: SearchStatus, searchedResult: List<RepositoryItem>) {
    Column(
        modifier = Modifier
            .backgroundBySearchStatus(searchStatus)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = when (searchStatus) {
                SearchStatus.READY -> stringResource(R.string.request_keyword)
                SearchStatus.SEARCHING -> stringResource(R.string.searching_msg)
                SearchStatus.SUCCESS -> stringResource(R.string.success_msg,"${searchedResult.size}")
                SearchStatus.NONE_KEYWORD -> stringResource(R.string.none_keyword)
                SearchStatus.FAILED -> stringResource(R.string.error_msg)
            },
            color = colorBySearchStatus(searchStatus),
            textAlign = TextAlign.Center,
        )
    }

}

fun Modifier.backgroundBySearchStatus(searchStatus: SearchStatus):Modifier{
    return when(searchStatus){
        SearchStatus.READY -> this
        SearchStatus.SEARCHING -> this
        SearchStatus.SUCCESS -> this.background(Success)
        SearchStatus.FAILED -> this.background(Error)
        SearchStatus.NONE_KEYWORD -> this.background(Error)
    }
}

fun colorBySearchStatus(searchStatus: SearchStatus):Color{
    return when(searchStatus){
        SearchStatus.READY -> Color.Black
        else -> Color.White
    }
}