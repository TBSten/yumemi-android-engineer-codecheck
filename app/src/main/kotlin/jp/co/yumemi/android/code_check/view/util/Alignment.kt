package jp.co.yumemi.android.code_check.view.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Left(content :@Composable ()->Unit){
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        content()
    }
}
@Composable
fun Right(content :@Composable ()->Unit){
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
    ) {
        content()
    }
}
@Composable
fun Center(content :@Composable ()->Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        content()
    }
}


@Preview
@Composable
fun AlignmentPreview(){
    Column {
        Left{ Text("left") }
        Center{ Text("center") }
        Right{ Text("right") }
    }
}