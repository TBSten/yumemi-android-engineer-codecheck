/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.model.RepositoryApi
import jp.co.yumemi.android.code_check.model.RepositoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * レポジトリを検索したり保持したりする
 *
 * - searchedRepositoryItemsには検索結果
 * - searchStatusには検索の状態("検索中"など)
 *
 * - searchResults(キーワード)で検索
 *
 * ```kotlin
 * //使用例
 * //searchedResult,searchStatusは描画に使用
 * val searchedResult by viewModel.searchedRepositoryItems.observeAsState(listOf())
 * val searchStatus by viewModel.searchStatus.observeAsState(SearchStatus.READY)
 *
 * //イベントハンドラなどで検索
 * Button( onClick = { searchResults("kotlin") } ){...}
 *
 * ```
 */
class SearchViewModel : ViewModel() {

    private var _searchedRepositoryItems: MutableLiveData<List<RepositoryItem>> =
        MutableLiveData(listOf())

    /**
     * 検索結果一覧
     */
    val searchedRepositoryItems: MutableLiveData<List<RepositoryItem>> = _searchedRepositoryItems

    private var _searchStatus = MutableLiveData(SearchStatus.READY)

    /**
     * 検索の状態
     */
    var searchStatus: MutableLiveData<SearchStatus> = _searchStatus

    /**
     * 検索時のキャッシュ。キーワードをキーに検索結果のレポジトリ一覧を保存する。
     */
    private var cache: Map<String, List<RepositoryItem>> = mutableMapOf()

    /**
     * Githubからレポジトリを検索する
     */
    fun searchResults(keyword: String) {
        //キーワードが空白だった場合はキーワードなしを返す
        if (keyword.matches(Regex("^(\\s*)$"))) {
            searchStatus.value = SearchStatus.NONE_KEYWORD
            return
        }

        //検索結果が見つかった時に実行する
        fun onResolved(repos: List<RepositoryItem>) {
            searchStatus.value = SearchStatus.SUCCESS
            searchedRepositoryItems.value = repos
            //検索結果を再利用できるようにキャッシュする
            cacheRepositoryItem(keyword, repos)
        }
        //キャッシュ内にキーワードがあった場合
        val cachedResult = getCachedSearchResult(keyword)
        if (cachedResult != null) {
            return onResolved(cachedResult)
        }
        //Githubから検索する
        viewModelScope.launch(Dispatchers.Main) {
            try {
                searchStatus.value = SearchStatus.SEARCHING
                //実際に検索する
                var repos =
                    RepositoryApi()
                        .query(keyword)
                        .getItems()
                onResolved(repos)
            } catch (e: Exception) {
                Log.e("", "error", e)
                searchStatus.value = SearchStatus.FAILED
            }
        }
    }

    /**
     * キーワードと検索結果を結びつけてキャッシュします。
     * キャッシュした検索結果は同じキーワードでの次回以降の検索に使用されます。
     */
    private fun cacheRepositoryItem(keyword: String, repo: List<RepositoryItem>) {
        cache += (keyword to repo)
    }

    /**
     * キャッシュされた検索結果を取得する
     */
    private fun getCachedSearchResult(keyword: String): List<RepositoryItem>? {
        return cache.getOrDefault(keyword, null)
    }

}

enum class SearchStatus {
    READY,
    SEARCHING,
    SUCCESS,
    FAILED,
    NONE_KEYWORD,
}
