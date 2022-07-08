package jp.co.yumemi.android.code_check.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.tbsten.memoappcompose.ui.theme.CodeCheckAppComposeTheme
import jp.co.yumemi.android.code_check.R

@Composable
fun BaseLayout(
    title: String = stringResource(R.string.app_name),
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
    children: @Composable () -> Unit,
) {
    CodeCheckAppComposeTheme {
        Scaffold(
            floatingActionButton = floatingActionButton,
            isFloatingActionButtonDocked = true,
        ) {
            Column {
                TopAppBar(
                    title = {
                        Text(text = title)
                    },
                    actions = { actions() },
                )
                children()
            }
        }
    }
}

