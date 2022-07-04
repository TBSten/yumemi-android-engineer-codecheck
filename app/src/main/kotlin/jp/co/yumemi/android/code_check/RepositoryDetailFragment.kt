/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentRepositoryDetailBinding
import jp.co.yumemi.android.code_check.util.withCatch
import jp.co.yumemi.android.code_check.model.RepositoryItem

class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        setViewByItem(view, args.item)
    }

    private fun setViewByItem(view: View, item: RepositoryItem) {
        val binding = FragmentRepositoryDetailBinding.bind(view)
        binding.run {
            withCatch(this@RepositoryDetailFragment.getString(R.string.img_failure)) {
                ownerIconView.load(item.ownerIconUrl)
            }
            nameView.text = item.name;
            languageView.text = getString(R.string.written_language, item.language);
            starsView.text = "${item.stargazersCount} stars";
            watchersView.text = "${item.watchersCount} watchers";
            forksView.text = "${item.forksCount} forks";
            openIssuesView.text = "${item.openIssuesCount} open issues";
        }
    }

}

