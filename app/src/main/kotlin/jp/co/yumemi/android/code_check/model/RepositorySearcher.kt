package jp.co.yumemi.android.code_check.model

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.*

class RepositorySearcher {
    companion object {
        private val client = HttpClient(Android)        //クライアントは使い回す
    }

    private var keywords: String = ""

    /**
     * キーワードを追加する
     */
    fun query(keyword: String): RepositorySearcher {
        keywords += "$keyword "
        return this
    }

    /**
     * リクエストを送信してレポジトリ一覧をListとして返す
     */
    fun getItems(): List<RepositoryItem> = runBlocking {
        val response = requestToGithub(keywords)
        //response.body.itemsを取得
        val jsonItems = JSONObject(response.receive<String>())
            .optJSONArray("items")
            ?: throw IllegalArgumentException("invalid client")
        //jsonItemsをにして返す
        mutableListOf<RepositoryItem>().also {
            for (i in 0 until jsonItems.length()) {
                val jsonItem = jsonItems.optJSONObject((i)) ?: continue
                val item = jsonToRepositoryItem(jsonItem)
                it.add(item)
            }
        }
    }

    /**
     * Githubから検索する
     * lastSearchDateが更新される
     */
    private fun requestToGithub(query: String): HttpResponse = runBlocking {
        return@runBlocking GlobalScope.async {
            val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", query)
            }
            lastSearchDate = Date()
            return@async response
        }.await()
    }

}
