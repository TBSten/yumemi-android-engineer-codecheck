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

class RepositoryDetailFragment : DialogFragment(R.layout.fragment_repository_detail) {

    private val args: RepositoryDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentRepositoryDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentRepositoryDetailBinding.bind(view)

        var item = args.item

        withCatch("画像が読み込めませんでした"){
            binding.ownerIconView.load(item.ownerIconUrl)
        }
        binding.nameView.text = item.name;
        binding.languageView.text = item.language;
        binding.starsView.text = "${item.stargazersCount} stars";
        binding.watchersView.text = "${item.watchersCount} watchers";
        binding.forksView.text = "${item.forksCount} forks";
        binding.openIssuesView.text = "${item.openIssuesCount} open issues";
    }
}
