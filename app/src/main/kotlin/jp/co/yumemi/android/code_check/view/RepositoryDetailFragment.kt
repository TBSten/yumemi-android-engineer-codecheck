/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.view

import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import jp.co.yumemi.android.code_check.model.RepositoryItem
import jp.co.yumemi.android.code_check.ui.component.BaseLayout

//class RepositoryDetailFragment : Fragment(R.layout.fragment_repository_detail) {
//
//    private val args: RepositoryDetailFragmentArgs by navArgs()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        Log.d("検索した日時", lastSearchDate.toString())
//
//        setViewByItem(view, args.item)
//    }
//
//    private fun setViewByItem(view: View, item: RepositoryItem) {
//        val binding = FragmentRepositoryDetailBinding.bind(view)
//        binding.run {
//            withCatch(this@RepositoryDetailFragment.getString(R.string.img_failure)) {
//                ownerIconView.load(item.ownerIconUrl)
//            }
//            nameView.text = item.name;
//            languageView.text = getString(R.string.written_language, item.language);
//            starsView.text = "${item.stargazersCount} stars";
//            watchersView.text = "${item.watchersCount} watchers";
//            forksView.text = "${item.forksCount} forks";
//            openIssuesView.text = "${item.openIssuesCount} open issues";
//        }
//    }
//
//}
//

@Composable
fun RepositoryScreen(repositoryItem: RepositoryItem){
    BaseLayout {
        Text("${repositoryItem.name}")
    }
}
