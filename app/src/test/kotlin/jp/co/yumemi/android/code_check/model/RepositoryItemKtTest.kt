package jp.co.yumemi.android.code_check.model

import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert.*
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
    object TestRepositoryItem {
        const val name = "JetBrains/kotlin"
        const val ownerIconUrl = "https://avatars.githubusercontent.com/u/878437?v=4"
        const val language = "Kotlin"
        const val stargazersCount = 41939L
        const val watchersCount = 41939L
        const val forksCount = 5197L
        const val openIssuesCount = 154L
    }
    @Test
    fun whenInputValidJsonToRepositoryItem() {

        val json = JSONObject()
        json.put("full_name", TestRepositoryItem.name)
        json.put("owner", JSONObject().run { put("avatar_url", TestRepositoryItem.ownerIconUrl) })
        json.put("language", TestRepositoryItem.language)
        json.put("stargazers_count", TestRepositoryItem.stargazersCount)
        json.put("watchers_count", TestRepositoryItem.watchersCount)
        json.put("forks_count", TestRepositoryItem.forksCount)
        json.put("open_issues_count", TestRepositoryItem.openIssuesCount)

        val result = jsonToRepositoryItem(json)
        val expected = RepositoryItem(
            name = TestRepositoryItem.name,
            ownerIconUrl = TestRepositoryItem.ownerIconUrl,
            language = TestRepositoryItem.language,
            stargazersCount = TestRepositoryItem.stargazersCount,
            watchersCount = TestRepositoryItem.watchersCount,
            openIssuesCount = TestRepositoryItem.openIssuesCount,
            forksCount = TestRepositoryItem.forksCount,
        )
        assertEquals(expected.name, result.name)
        assertEquals(expected.ownerIconUrl, result.ownerIconUrl)
        assertEquals(expected.language, result.language)
        assertEquals(expected.stargazersCount, result.stargazersCount)
        assertEquals(expected.watchersCount, result.watchersCount)
        assertEquals(expected.forksCount, result.forksCount)
        assertEquals(expected.openIssuesCount, result.openIssuesCount)
    }

    @Test(expected = JSONException::class)
    fun whenThereIsNoName() {
        val json = JSONObject()
        json.put("owner", JSONObject().run { put("avatar_url", TestRepositoryItem.ownerIconUrl) })
        json.put("language", TestRepositoryItem.language)
        json.put("stargazers_count", TestRepositoryItem.stargazersCount)
        json.put("watchers_count", TestRepositoryItem.watchersCount)
        json.put("forks_count", TestRepositoryItem.forksCount)
        json.put("open_issues_count", TestRepositoryItem.openIssuesCount)

        jsonToRepositoryItem(json)  //required throw
    }
    @Test(expected = JSONException::class)
    fun whenThereIsNoProps() {
        val json = JSONObject()
        jsonToRepositoryItem(json)  //required throw
    }

}

