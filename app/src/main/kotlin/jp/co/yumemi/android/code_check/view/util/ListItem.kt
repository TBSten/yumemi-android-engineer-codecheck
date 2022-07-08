package jp.co.yumemi.android.code_check.view.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    text: String,
    onClick: (() -> Unit)? = null,
    icon: @Composable () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick ?: {}, enabled = onClick != null)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Box(modifier = Modifier
                .weight(1F)
                .clickable(enabled = onClick != null) {
                    onClick?.invoke()
                }
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(
                            vertical = 18.dp,
                            horizontal = 9.dp,
                        )
                )
            }
            icon()
        }
        Divider()
    }
}
