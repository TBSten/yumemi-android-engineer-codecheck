/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.room.Room
import dev.tbsten.memoappcompose.ui.theme.CodeCheckAppComposeTheme
import jp.co.yumemi.android.code_check.model.AppDatabase
import jp.co.yumemi.android.code_check.view.screen.navigate.rememberAppNavController
import java.util.*

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var lastSearchDate: Date
        lateinit var database :AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
        setContent {
            ActivityContent()
        }
    }
}

@Composable
fun ActivityContent() {
    CodeCheckAppComposeTheme {
        val appNav = rememberAppNavController()
        appNav.NavHost()
    }
}

