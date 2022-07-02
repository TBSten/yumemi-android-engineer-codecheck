package jp.co.yumemi.android.code_check

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class DialogFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    /**
     * usage:
     * <pre><code>
     * buildDialog(this)
     *      <!--  .settings()  -->
     *      .show()
     * </code></pre>
     */
    fun buildDialog(
        title: String,
        message: String,
        onYes: DialogInterface.OnClickListener? = null,
        onCancel: DialogInterface.OnClickListener? = null,
    ): AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
        if (onYes != null) {
            builder.setPositiveButton("OK", onYes)
        }
        if (onCancel != null) {
            builder.setNegativeButton("キャンセル", onYes)
        }
        return builder
    }

    fun <ResultType> withCatch(
        func: () -> ResultType,
    ): ResultType? {
        return withCatch("error occured", func)
    }

    fun <ResultType> withCatch(
        errMsg: String = getString(R.string.error_alert_title),
        func: () -> ResultType,
    ): ResultType? {
        try {
            return func()
        } catch (e: Exception) {  //TODO: InputErrorやResourceErrorなど既知のエラーについてはそれに対応した処理をする
            Log.e("error", errMsg, e)
            buildDialog(
                errMsg,
                getString(R.string.error_alert_message),
            ).show()
            return null
        }
    }

}

