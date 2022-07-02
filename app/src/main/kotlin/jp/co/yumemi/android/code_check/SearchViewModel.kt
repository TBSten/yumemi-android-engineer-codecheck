/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.parcelize.Parcelize
import org.json.JSONObject
import java.lang.System.identityHashCode
import java.util.*

/**
 * SearchFragment で使う
 */
class SearchViewModel(
    private val context: Context
) : ViewModel() {

    private val client = HttpClient(Android)        //クライアントを使い回すためにフィールドへ移動

    /**
     * Githubからレポジトリを検索する
     *
     * lastSearchDateが更新される
     */
    fun searchResults(inputText: String): List<item> = runBlocking {
        Log.d("","search ${identityHashCode(client)}")
        return@runBlocking GlobalScope.async {
            if (client == null) {
                throw IllegalArgumentException("invalid client")
            }
            // web api へリクエストを送る
            val response = requestSearchToGithub(inputText)

            //response.body.itemsを取得
            val jsonItems = JSONObject(response.receive<String>())
                .optJSONArray("items") ?: throw IllegalArgumentException("invalid client")

            // 検索結果を返す
            val items = mutableListOf<item>().also {
                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject((i)) ?: break
                    val item = jsonToItem(jsonItem, context)
                    it.add(item)
                }
            }
            //最終更新日を更新する
            lastSearchDate = Date()
            return@async items.toList()
        }.await()
    }

    /**
     * Githubへ検索のためのHttpリクエストを送る
     */
    private fun requestSearchToGithub(inputText: String): HttpResponse = runBlocking {
        return@runBlocking GlobalScope.async {
            val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }
            return@async response
        }.await()
    }

}

@Parcelize
data class item(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable {
}

fun jsonToItem(jsonItem: JSONObject, context: Context): item {
    return jsonItem.let {
        val name = it.optString("full_name")
        val ownerIconUrl = it.optJSONObject("owner").let { obj ->
            obj ?: throw IllegalArgumentException("invalid jsonItem.owner")
            obj.optString("avatar_url")
        }
        val language = it.optString("language")
        val stargazersCount = it.optLong("stargazers_count")
        val watchersCount = it.optLong("watchers_count")
        val forksCount = it.optLong("forks_conut")
        val openIssuesCount = it.optLong("open_issues_count")
        item(
            name = name,
            ownerIconUrl = ownerIconUrl,
            language = context.getString(R.string.written_language, language),
            stargazersCount = stargazersCount,
            watchersCount = watchersCount,
            forksCount = forksCount,
            openIssuesCount = openIssuesCount
        )
    }
}
