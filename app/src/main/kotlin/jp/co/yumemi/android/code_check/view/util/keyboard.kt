package jp.co.yumemi.android.code_check.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

/**
 * キーボードを閉じる
 *
 * ```
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun useKeyboardHide() :()->Unit{
    val keyboardController = LocalSoftwareKeyboardController.current
    fun hide(){
        keyboardController?.hide()
    }
    return ::hide
}

