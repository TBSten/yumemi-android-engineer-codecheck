/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.RepositoryItem
import jp.co.yumemi.android.code_check.ui.component.BaseLayout
import jp.co.yumemi.android.code_check.viewmodel.DetailViewModel
import kotlinx.coroutines.launch

///**
// * レポジトリ検索ページ
// */
//class SearchFragment : Fragment(R.layout.fragment_search) {
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val _binding = FragmentSearchBinding.bind(view)
//        val _viewModel = DetailViewModel()
//
//        // 検索結果の情報の表示と各行クリック時の動作
//        val repositoryItemAdapter =
//            RepositoryItemAdapter(object : RepositoryItemAdapter.OnItemClickListener {
//                override fun itemClick(item: RepositoryItem) {
//                    gotoDetailFragment(item)
//                }
//            })
//
//        _binding.searchInputText.apply {
//            setOnEditorActionListener { editText, action, _ ->
//                //検索時以外は 何もしない
//                if (action != EditorInfo.IME_ACTION_SEARCH) {
//                    return@setOnEditorActionListener false
//                }
//                //キーボードを閉じる
//                hideKeyboard()
//                //検索する
//                withCatch(getString(R.string.search_failure)) {
//                    //入力された文字列をもとにGithubから検索する
//                    val inputText = editText.text.toString()
//                    val searchedResult = _viewModel.searchResults(inputText)
//                    //検索結果をもとに画面を更新する
//                    repositoryItemAdapter.submitList(searchedResult)
//                }
//                return@setOnEditorActionListener true
//            }
//        }
//
//        _binding.recyclerView.apply {
//            val _layoutManager = LinearLayoutManager(requireContext())
//            layoutManager = _layoutManager
//            addItemDecoration(DividerItemDecoration(requireContext(), _layoutManager.orientation))
//            adapter = repositoryItemAdapter
//        }
//    }
//
//    /**
//     * RepositoryDetailFragmentへ画面遷移する
//     * 引数としてitemを渡す
//     * ここで渡したitemをもとにRepositoryDetailFragmentが表示される
//     */
//    private fun gotoDetailFragment(item: RepositoryItem) {
//        val _action = SearchFragmentDirections
//            .actionListFragmentToDetailFragment(item = item)
//        findNavController().navigate(_action)
//    }
//
//    /**
//     * キーボードを隠す
//     */
//    private fun hideKeyboard() {
//        try {
//            val inputManager =
//                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputManager.hideSoftInputFromWindow(
//                view?.windowToken,
//                InputMethodManager.HIDE_NOT_ALWAYS
//            )
//        } catch (e: ClassCastException) {
//            Log.e("", "error occurred , can not hide keyboard", e)
//        }
//    }
//
//}
//
//val diff_util = object : DiffUtil.ItemCallback<RepositoryItem>() {
//    override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
//        return oldItem.name == newItem.name
//    }
//
//    override fun areContentsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem): Boolean {
//        return oldItem == newItem
//    }
//
//}
//
//class RepositoryItemAdapter(
//    private val itemClickListener: OnItemClickListener,
//) : ListAdapter<RepositoryItem, RepositoryItemAdapter.ViewHolder>(diff_util) {
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
//
//    interface OnItemClickListener {
//        fun itemClick(item: RepositoryItem)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val _view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.layout_item, parent, false)
//        return ViewHolder(_view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val _item = getItem(position)
//
//        //repositoryNameView
//        val textView = holder.itemView.findViewById<View>(R.id.repositoryNameView)
//        if (textView !is TextView) {
//            throw UnsupportedOperationException("invalid textView type")
//        }
//        textView.text = _item.name
//
//        holder.itemView.setOnClickListener {
//            itemClickListener.itemClick(_item)
//        }
//    }
//}

// Compose

@Composable
fun SearchScreen(
    viewModel: DetailViewModel = viewModel(),
    gotoRepositoryDetail: (RepositoryItem) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var searchedResult by remember { mutableStateOf<List<RepositoryItem>>(listOf()) }
    fun handleSearch(keyword: String = inputText) {
        searchedResult = viewModel.searchResults(keyword)
    }
    BaseLayout {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                SearchTextInput(
                    value = inputText,
                    onValueChange = { inputText = it },
                    onSearch = ::handleSearch,
                    modifier = Modifier.weight(weight = 1f, fill = true),
                )
                Button(onClick = ::handleSearch) {
                    Text(
                        text = stringResource(R.string.search_button_text),
                    )
                }
            }
            SearchResultList(
                repositoryItems = searchedResult,
                gotoRepositoryDetail = gotoRepositoryDetail,
            )
        }
    }

}

@Composable
fun SearchTextInput(
    value: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: (keyword: String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = { Text(text = "キーワードを入力") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(value) },
        ),
        modifier = modifier,
    )
}

@Composable
fun SearchResultList(
    repositoryItems: List<RepositoryItem>,
    gotoRepositoryDetail: (RepositoryItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 9.dp)
    ) {
        repositoryItems.forEach {
            SearchResultListItem(
                text = it.name,
                onClick = { gotoRepositoryDetail(it) }
            )
        }
        if (repositoryItems.isEmpty()) {
            SearchResultListItem(text = stringResource(R.string.nothing_search_result))
        }
    }
}

@Composable
fun SearchResultListItem(
    text: String,
    onClick: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.let {
            if (onClick != null) it.clickable(onClick = onClick) else it
        }
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                vertical = 18.dp,
                horizontal = 9.dp,
            )
        )
        Divider()
    }
}