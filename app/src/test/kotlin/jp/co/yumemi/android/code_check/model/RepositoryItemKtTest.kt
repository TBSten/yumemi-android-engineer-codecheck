package jp.co.yumemi.android.code_check.model

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

fun validData(num: Int, name: String): JSONObject {
    return JSONObject().run {
        put("num", num)
        put("name", name)
    }
}

@RunWith(JUnit4::class)
class RepositoryItemKtTest {
    @Test
    fun whenInputValidJsonToRepositoryItem() {
        val name = "JetBrains/kotlin"
        val ownerIconUrl = "https://avatars.githubusercontent.com/u/878437?v=4"
        val language = "Kotlin"
        val stargazersCount = 41939L
        val watchersCount = 41939L
        val forksCount = 5197L
        val openIssuesCount = 154L

        val json = JSONObject()
        json.put("full_name",name)
        json.put("owner", JSONObject().run { put("avatar_url", ownerIconUrl) })
        json.put("language", language)
        json.put("stargazers_count", stargazersCount)
        json.put("watchers_count", watchersCount)
        json.put("forks_count", forksCount)
        json.put("open_issues_count", openIssuesCount)

        val result = jsonToRepositoryItem(json)
        val expected = RepositoryItem(
            name = name,
            ownerIconUrl = ownerIconUrl,
            language = language,
            stargazersCount = stargazersCount,
            watchersCount = watchersCount,
            forksCount = forksCount,
            openIssuesCount = openIssuesCount,
        )
        assertEquals(expected.name, result.name)
        assertEquals(expected.ownerIconUrl, result.ownerIconUrl)
        assertEquals(expected.language, result.language)
        assertEquals(expected.stargazersCount, result.stargazersCount)
        assertEquals(expected.watchersCount, result.watchersCount)
        assertEquals(expected.forksCount, result.forksCount)
        assertEquals(expected.openIssuesCount, result.openIssuesCount)

    }
}

