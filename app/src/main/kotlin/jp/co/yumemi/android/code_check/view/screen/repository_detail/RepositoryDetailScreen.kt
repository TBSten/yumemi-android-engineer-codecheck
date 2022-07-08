/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.view.screen.repository_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.RepositoryItem
import jp.co.yumemi.android.code_check.view.component.BaseLayout
import jp.co.yumemi.android.code_check.view.screen.navigate.AppNavController
import jp.co.yumemi.android.code_check.view.util.Left

/**
 * 個々のレポジトリの詳細ページ
 */
@Composable
fun RepositoryDetailScreen(
    appNav :AppNavController,
    repositoryItem: RepositoryItem?,
) {
    if(repositoryItem == null){
        Text(text = "エラーが発生しました")
        return
    }
    BaseLayout(
        title = repositoryItem.name,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                appNav.navigateSearch()
            }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Overview(
                name = repositoryItem.name,
                iconUrl = repositoryItem.ownerIconUrl
            )
            Detail(repositoryItem)
        }
    }
}

/**
 * 画像とレポジトリ名を表示
 */
@Composable
fun Overview(
    name: String,
    iconUrl: String,
) {
    Box(
        modifier = Modifier.fillMaxWidth().background(Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp, horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SubcomposeAsyncImage(
                model = iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(160.dp)
                    .padding(end = 6.dp),
                loading = {
                    LinearProgressIndicator(modifier = Modifier.size(20.dp))
                }
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                text = name,
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

/**
 * 詳細情報を表示
 */
@Composable
fun Detail(
    repo: RepositoryItem
) {
    Box(
        modifier = Modifier.padding(horizontal = 6.dp, vertical = 30.dp)
    ) {
        val recordTexts = listOf(
            stringResource(R.string.repo_detail_stars, repo.stargazersCount),
            stringResource(R.string.repo_detail_watchers, repo.watchersCount),
            stringResource(R.string.repo_detail_forks, repo.forksCount),
            stringResource(R.string.repo_detail_open_issues, repo.openIssuesCount),
        )
        Column {
            Left { Text(stringResource(R.string.repo_detail_lang, repo.language)) }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
            ) {
                items(recordTexts.size) { i ->
                    DetailText(recordTexts[i])
                }
            }
        }
    }
}

@Composable
fun DetailText(text: String) {
    Text(
        modifier = Modifier.padding(5.dp),
        text = text,
    )
}
