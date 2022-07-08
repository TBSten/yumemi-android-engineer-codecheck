package jp.co.yumemi.android.code_check.view.screen.navigate

import android.net.Uri
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import jp.co.yumemi.android.code_check.model.RepositoryItem
import jp.co.yumemi.android.code_check.view.screen.keep.KeepScreen
import jp.co.yumemi.android.code_check.view.screen.repository_detail.RepositoryDetailScreen
import jp.co.yumemi.android.code_check.view.screen.search.SearchScreen

class RepositoryItemNavType : NavType<RepositoryItem>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): RepositoryItem? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): RepositoryItem {
        return Gson().fromJson(value, RepositoryItem::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: RepositoryItem) {
        bundle.putParcelable(key, value)
    }

}

/**
 * NavHostControllerを当アプリ用にラッピングするクラス。
 * goto◯◯メソッドを呼び出すことで各ページへ移動できる
 */
class AppNavController(
    private val navController: NavHostController,
) {
    @Composable
    fun NavHost() {
        androidx.navigation.compose.NavHost(
            this.navController,
            startDestination = Routes.SEARCH.name
        ) {
            composable(route = Routes.SEARCH.name) {
                SearchScreen(
                    appNav = this@AppNavController,
                )
            }
            composable(
                route = "${Routes.REPOSITORY_DETAIL.name}/{repositoryItem}",
                arguments = listOf(navArgument("repositoryItem") { type = RepositoryItemNavType() })
            ) {
                val repo = it.arguments?.getParcelable<RepositoryItem>("repositoryItem")
                RepositoryDetailScreen(
                    appNav = this@AppNavController,
                    repositoryItem = repo,
                )
            }
            composable(route = Routes.KEEP.name) {
                KeepScreen(
                    appNav = this@AppNavController,
                )
            }
        }

    }

    /**
     * 検索ページへ遷移する
     */
    fun navigateSearch() {
        navController.navigate(Routes.SEARCH.name)
    }

    /**
     * レポジトリの詳細ページへ移動する
     */
    fun navigateRepositoryDetail(repo: RepositoryItem) {
        val json = Uri.encode(Gson().toJson(repo))
        navController.navigate("${Routes.REPOSITORY_DETAIL.name}/$json")
    }

    fun navigateKeep() {
        navController.navigate(Routes.KEEP.name)
    }

    fun navigateUp() {
        navController.navigateUp()
    }

}


@Composable
fun rememberAppNavController(): AppNavController {
    val navController = rememberNavController()
    return AppNavController(navController = navController)
}

