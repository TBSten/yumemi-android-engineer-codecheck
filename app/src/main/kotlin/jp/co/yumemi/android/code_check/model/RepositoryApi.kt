package jp.co.yumemi.android.code_check.model

import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 *
 * Githubレポジトリを検索するクラス。
 *
 * # usage
 * ```kotlin
 * val repos = RepositorySearcher()
 *      .query("キーワード1")
 *      .query("キーワード2")
 *      .getItems()
 * ```
 *
 */
class RepositoryApi {
    companion object {
        private val client = HttpClient(Android)        //クライアントは使い回す
    }

    private var keywords: String = ""

    /**
     * キーワードを追加する
     */
    fun query(keyword: String): RepositoryApi {
        keywords += "$keyword "
        return this
    }

    /**
     * リクエストを送信してレポジトリ一覧をListとして返す
     */
    suspend fun getItems(): List<RepositoryItem> {
        val body = requestToGithub(keywords)
        //body.itemsを取得
        val items = JSONObject(body).optJSONArray("items")
        return mutableListOf<RepositoryItem>().apply {
            //items.foreach
            for (i in 0 until items.length()) {
                add(jsonToRepositoryItem(items.optJSONObject(i)))
            }
        }
    }

    /**
     * Githubから検索する
     * lastSearchDateが更新される
     */
    private suspend fun requestToGithub(query: String): String? {
        val client = OkHttpClient.Builder()
            .connectTimeout(3000L, TimeUnit.MILLISECONDS)
            .readTimeout(5000L, TimeUnit.MILLISECONDS)
            .build()
        val request = Request.Builder()
            .url("https://api.github.com/search/repositories?q=$query")
            .build()
        val body = GlobalScope.async {
            val res = client.newCall(request).execute()
            res.body?.string().orEmpty()
        }.await()
        return body
    }

}
