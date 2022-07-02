/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding

/**
 * レポジトリ検索ページ
 */
class SearchFragment : DialogFragment(R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _binding = FragmentSearchBinding.bind(view)
        val _viewModel = DetailViewModel(requireContext())
        val _layoutManager = LinearLayoutManager(requireContext())
        val _dividerItemDecoration =
            DividerItemDecoration(requireContext(), _layoutManager.orientation)

        // 検索結果の各行クリック時の動作
        val _adapter = ItemClickAdapter(object : ItemClickAdapter.OnItemClickListener {
            override fun itemClick(item: item) {
                gotoDetailFragment(item)
            }
        })

        _binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                //TODO: エンターキーが押された時以外は `return@setOnEditorActionListener false`
                if(action != EditorInfo.IME_ACTION_SEARCH){
                    return@setOnEditorActionListener false
                }
                withCatch ("検索中にエラーが発生しました"){
                    //入力された文字列をもとにGithubから検索する
                    val inputText = editText.text.toString()
                    val searchedResult = _viewModel.searchResults(inputText)
                    //検索結果をもとに画面を更新する
                    _adapter.submitList(searchedResult)
                }
                return@setOnEditorActionListener true
            }

        _binding.recyclerView.also {
            it.layoutManager = _layoutManager
            it.addItemDecoration(_dividerItemDecoration)
            it.adapter = _adapter
        }
    }

    fun gotoDetailFragment(item: item) {
        val _action = SearchFragmentDirections
            .actionListFragmentToDetailFragment(item = item)
        findNavController().navigate(_action)
    }
}


val diff_util = object : DiffUtil.ItemCallback<item>() {
    override fun areItemsTheSame(oldItem: item, newItem: item): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: item, newItem: item): Boolean {
        return oldItem == newItem
    }

}

class ItemClickAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<item, ItemClickAdapter.ViewHolder>(diff_util) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun itemClick(item: item)
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
