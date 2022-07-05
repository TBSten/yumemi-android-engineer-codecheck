package jp.co.yumemi.android.code_check

import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)
    private val keywords = listOf("kotlin", "android", "yumemi")

    @Test
    fun searchSingleKeyword() {
        val keyword = keywords[0]

        onView(withId(R.id.searchInputText))
            .perform(typeText(keyword))
            .perform(ViewActions.pressImeActionButton())

        onView(withId(R.id.recyclerView))
            .check(RecyclerViewItemCountAssertion("none result when searched by $keyword"))

    }

    @Test
    fun searchMultiKeyword() {
        onView(withId(R.id.searchInputText)).apply {
            keywords.forEach { keyword ->
                perform(typeText(keyword))
                perform(typeText(" "))
            }
            perform(ViewActions.pressImeActionButton())
        }

        onView(withId(R.id.recyclerView))
            .check(RecyclerViewItemCountAssertion("none result when searched by $keywords"))

    }

    @Test
    fun noneResult() {
        // 検索時に1件も見つからなかった時に「結果がありませんと表示」
    }

}


class RecyclerViewItemCountAssertion(
    private val errMsg: String = "invalid recyclerView.adapter.count"
) : ViewAssertion {
    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(
            errMsg,
            adapter?.itemCount,
            not(0)
        )
    }
}

