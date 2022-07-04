package jp.co.yumemi.android.code_check.util

import android.app.AlertDialog
import android.util.Log
import androidx.fragment.app.Fragment
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


/**
 * エラーをキャッチして特定の処理を実行する
 */

fun <T : Fragment> T.withCatch(
    errMsg: String = getString(R.string.error_alert_title),
    func: () -> Unit,
){
    return try {
        func()
    } catch (e: Exception) {  //TODO: InputErrorやResourceErrorなど既知のエラーについてはそれに対応した処理をする
        Log.e("error", errMsg, e)
        AlertDialog.Builder(this.requireContext())
            .setTitle(errMsg)
            .setMessage(getString(R.string.error_alert_message))
            .show()
        return
    }

}

