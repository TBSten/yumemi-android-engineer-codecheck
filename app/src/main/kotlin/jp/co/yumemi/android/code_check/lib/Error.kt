package jp.co.yumemi.android.code_check.lib

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


