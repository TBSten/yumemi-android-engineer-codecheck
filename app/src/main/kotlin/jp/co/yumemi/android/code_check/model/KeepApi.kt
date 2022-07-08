package jp.co.yumemi.android.code_check.model

import android.util.Log

/**
 * キープ(レポジトリのピン留め機能)を保存する
 */
class KeepApi {

    fun save(
        keeps: List<RepositoryItem>,
    ) {
        Log.w("","cannot save keeps")
    }

    fun load(): List<RepositoryItem> {
        Log.w("","cannot load keeps")
        return listOf()
    }

}

