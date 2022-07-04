/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding
import jp.co.yumemi.android.code_check.util.withCatch
import jp.co.yumemi.android.code_check.model.RepositoryItem
import jp.co.yumemi.android.code_check.viewmodel.DetailViewModel

/**
 * レポジトリ検索ページ
 */
class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _binding = FragmentSearchBinding.bind(view)
        val _viewModel = DetailViewModel()

        // 検索結果の情報の表示と各行クリック時の動作
        val repositoryItemAdapter =
            RepositoryItemAdapter(object : RepositoryItemAdapter.OnItemClickListener {
                override fun itemClick(item: RepositoryItem) {
                    gotoDetailFragment(item)
                }
            })

        _binding.searchInputText.apply {
            setOnEditorActionListener { editText, action, _ ->
                //検索時以外は 何もしない
                if (action != EditorInfo.IME_ACTION_SEARCH) {
                    return@setOnEditorActionListener false
                }
                //キーボードを閉じる
                hideKeyboard()
                //検索する
                withCatch(getString(R.string.search_failure)) {
                    //入力された文字列をもとにGithubから検索する
                    val inputText = editText.text.toString()
                    val searchedResult = _viewModel.searchResults(inputText)
                    //検索結果をもとに画面を更新する
                    repositoryItemAdapter.submitList(searchedResult)
                }
                return@setOnEditorActionListener true
            }
        }

        _binding.recyclerView.apply {
            val _layoutManager = LinearLayoutManager(requireContext())
            layoutManager = _layoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), _layoutManager.orientation))
            adapter = repositoryItemAdapter
        }
    }

    /**
     * RepositoryDetailFragmentへ画面遷移する
     * 引数としてitemを渡す
     * ここで渡したitemをもとにRepositoryDetailFragmentが表示される
     */
    private fun gotoDetailFragment(item: RepositoryItem) {
        val _action = SearchFragmentDirections
            .actionListFragmentToDetailFragment(item = item)
        findNavController().navigate(_action)
    }

    /**
     * キーボードを隠す
     */
    private fun hideKeyboard() {
        try {
            val inputManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (e: ClassCastException) {
            Log.e("", "error occurred , can not hide keyboard", e)
        }
    }

}

val diff_util = object : DiffUtil.ItemCallback<RepositoryItem>() {
    override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
        return oldItem == newItem
    }

}

class RepositoryItemAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<RepositoryItem, RepositoryItemAdapter.ViewHolder>(diff_util) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(item: RepositoryItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val _view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item, parent, false)
        return ViewHolder(_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val _item = getItem(position)

        //repositoryNameView
        val textView = holder.itemView.findViewById<View>(R.id.repositoryNameView)
        if (textView !is TextView) {
            throw UnsupportedOperationException("invalid textView type")
        }
        textView.text = _item.name

        holder.itemView.setOnClickListener {
            itemClickListener.itemClick(_item)
        }
    }
}
