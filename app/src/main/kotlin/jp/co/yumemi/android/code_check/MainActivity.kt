/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.view.SearchScreen
import java.util.*
import androidx.compose.material.Text
import androidx.navigation.compose.dialog

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var lastSearchDate: Date
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityContent()
        }
    }
}

@Composable
private fun ActivityContent() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "search"){
        this.dialog("repository_detail"){
            Text("detail page")
        }
        this.
        composable(route = "search"){
            SearchScreen(gotoRepositoryDetail = {
                Log.d("","goto detail")
                navController.navigate("repository_detail")
            })
        }
    }
}
