package jp.co.yumemi.android.code_check.util

import android.util.Log
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import jp.co.yumemi.android.code_check.R

/**
 * バリデーションに失敗するなどユーザに起因する例外
 */
class InputError(
    msg: String = "input error",
) : Exception(msg)

/**
 * レイアウトファイルで特定のidのviewがみつかないなど、プログラマに起因する例外
 */
class ResourceException(
    msg: String = "resource exception",
) : Exception(msg)


