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
        val name = it.getString("full_name")
        val ownerIconUrl = it.getJSONObject("owner").let { obj ->
            obj ?: throw IllegalArgumentException("invalid jsonItem.owner")
            obj.getString("avatar_url")
        }
        val language = it.getString("language")
        val stargazersCount = it.getLong("stargazers_count")
        val watchersCount = it.getLong("watchers_count")
        val forksCount = it.getLong("forks_count")
        val openIssuesCount = it.getLong("open_issues_count")
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
