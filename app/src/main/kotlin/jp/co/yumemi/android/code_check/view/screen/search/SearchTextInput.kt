package jp.co.yumemi.android.code_check.view.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.view.useKeyboardHide


@Composable
fun SearchTextInput(
    value: String,
    onValueChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: (keyword: String) -> Unit,
) {
    val hideKeyboard = useKeyboardHide()
    //検索する
    fun handleSearch(){
        hideKeyboard()
        onSearch(value)
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = { Text(text = stringResource(R.string.request_keyword)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { handleSearch() },
            ),
            modifier = modifier,
        )
        Box(Modifier.width(10.dp))
        Button( onClick = { handleSearch() } , modifier = Modifier.height(IntrinsicSize.Max)) {
            Text( text = stringResource(R.string.search_button_text) )
        }
    }
}
