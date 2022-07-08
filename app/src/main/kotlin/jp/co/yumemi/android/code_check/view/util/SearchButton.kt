package jp.co.yumemi.android.code_check.view.util

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun SearchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
){
    IconButton(onClick,modifier,enabled,interactionSource){
        Icon(Icons.Filled.Search, contentDescription = "search")
    }
}