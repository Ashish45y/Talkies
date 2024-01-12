package dev.ashish.talkies.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeTopBar(
    onSearchClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Default.MenuOpen,
                contentDescription = "menu",
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Talkies",
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive,
            fontSize = 24.sp,
        )

        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { onSearchClicked() }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search"
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    HomeTopBar(){

    }
}