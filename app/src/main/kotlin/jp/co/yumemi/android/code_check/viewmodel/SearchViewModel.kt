/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.lifecycle.ViewModel
import jp.co.yumemi.android.code_check.viewmodel.RepositoryItem
import jp.co.yumemi.android.code_check.viewmodel.RepositorySearcher

/**
 * DetailFragment で使う
 */
class DetailViewModel : ViewModel() {

    /**
     * Githubからレポジトリを検索する
     */
    fun searchResults(inputText: String): List<RepositoryItem> {
        return RepositorySearcher()
            .query(inputText)
            .getItems()
    }

}

