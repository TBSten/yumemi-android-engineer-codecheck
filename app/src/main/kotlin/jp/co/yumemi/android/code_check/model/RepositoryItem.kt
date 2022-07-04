package jp.co.yumemi.android.code_check.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject


@Parcelize
data class RepositoryItem(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable

fun jsonToRepositoryItem(jsonItem: JSONObject): RepositoryItem {
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
        RepositoryItem(
            name = name,
            ownerIconUrl = ownerIconUrl,
            language = language,
            stargazersCount = stargazersCount,
            watchersCount = watchersCount,
            forksCount = forksCount,
            openIssuesCount = openIssuesCount
        )
    }
}
