package jp.co.yumemi.android.code_check.model

import jp.co.yumemi.android.code_check.MainActivity

/**
 * キープ(レポジトリのピン留め機能)を保存する
 */
class KeepApi {

    fun save(
        keeps: List<RepositoryItem>,
    ) {
        val dao = MainActivity.database.keepDao()
        dao.saveKeepEntity(
            repositoryItemsToKeepEntity(keeps)
        )
    }

    fun load(): List<RepositoryItem> {
        val dao = MainActivity.database.keepDao()
        val keepEntity = dao.loadKeepEntity()[0]
        return keepEntityToRepositoryItems(keepEntity)
    }

}

