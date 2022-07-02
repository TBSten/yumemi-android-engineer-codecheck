package jp.co.yumemi.android.code_check

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlin.Exception

/**
 * バリデーションに失敗するなどユーザに起因する例外
 */
class InputError(
    msg:String = "search error",
):Exception()

/**
 * レイアウトファイルで特定のidのviewがみつかないなど、プログラマに起因する例外
 */
class ResourceException(
    msg:String = "search error",
):Exception()


/**
 * エラーをキャッチして特定の処理を実行する
 */
inline fun <R> catched(context:Context?,func: () -> R):R? {
    try {
        return func()
    } catch (e: Throwable) {
        Log.e("", "Error occurred", e)
        if(context != null){
            //TODO: アラートなどの表示
            AlertDialog.Builder(context)
                .setTitle("エラーが発生しました")
//                .setMessage(context.getString(R.string.error_alert_content)
        }
        return null
    }
}

//class DialogFragment(@LayoutRes contentLayoutId:Int): Fragment(contentLayoutId){
//    /**
//     * usage:
//     * <pre><code>
//     * buildDialog(this)
//     *      <!--  .settings()  -->
//     *      .show()
//     * </code></pre>
//     */
//    fun buildDialog(
//        title:String,
//        message:String
//    ):AlertDialog.Builder{
//        return AlertDialog.Builder(context)
//            .setTitle(title)
//            .setMessage(message)
//    }
//
//    /**
//     * usage:
//     * <pre><code>
//     *     msgDialog("タイトル","メッセージ"){
//     *          // OKが押された時の処理
//     *     }.show()
//     * </code></pre>
//     */
//    fun msgDialog(
//        title:String = "メッセージ",
//        message:String = "",
//        onYes: DialogInterface.OnClickListener?
//    ):AlertDialog.Builder{
//        //TODO: アラートなどの表示
//        return buildDialog(title, message)
//            .setPositiveButton("OK",onYes)
//    }
//    fun msgDialog(
//        title:String = "メッセージ",
//        message:String = "",
//    ):AlertDialog.Builder{
//        return msgDialog(
//            title,
//            message,
//            { dialog,which -> Unit }
//        )
//    }
//    fun confirmDialog(
//        title:String = "",
//        message: String,
//        onYes: DialogInterface.OnClickListener,
//        onCancel:DialogInterface.OnClickListener,
//    ):AlertDialog.Builder{
//        return buildDialog(title, message)
//            .setPositiveButton("OK",onYes)
//            .setNegativeButton("キャンセル",onCancel)
//
//    }
//
//
//}
