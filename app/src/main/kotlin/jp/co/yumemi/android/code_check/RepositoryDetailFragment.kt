/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding

class RepositoryDetailFragment : DialogFragment(R.layout.fragment_repository_detail) {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        var binding: FragmentRepositoryDetailBinding = FragmentRepositoryDetailBinding.bind(view)

        var item = args.item

        FragmentRepositoryDetailBinding.bind(view).run {
            withCatch("画像が読み込めませんでした") {
                ownerIconView.load(item.ownerIconUrl)
            }
            nameView.text = item.name;
            languageView.text = item.language;
            starsView.text = "${item.stargazersCount} stars";
            watchersView.text = "${item.watchersCount} watchers";
            forksView.text = "${item.forksCount} forks";
            openIssuesView.text = "${item.openIssuesCount} open issues";
        }
    }
}
