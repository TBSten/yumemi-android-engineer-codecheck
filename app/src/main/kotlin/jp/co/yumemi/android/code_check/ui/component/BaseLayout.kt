package jp.co.yumemi.android.code_check.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.tbsten.memoappcompose.ui.theme.CodeCheckAppComposeTheme
import jp.co.yumemi.android.code_check.R

@Composable
fun BaseLayout(title:String = stringResource(R.string.app_name), children:@Composable ()->Unit){
    CodeCheckAppComposeTheme{
        Column{
            TopAppBar(
                title = {
                    Text(text = title)
                }
            )
            children()
        }
    }
}