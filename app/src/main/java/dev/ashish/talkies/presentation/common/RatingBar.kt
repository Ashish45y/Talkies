package dev.ashish.talkies.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    color: Color = Color(0xFFDE970B),
    onRatingChange: (Double) -> Unit
) {

    var isHalfStar = (rating%1)!=0.0

    Row{
        for(index in 1 .. stars){
            Icon(
                modifier = modifier.clickable { onRatingChange(index.toDouble()) },
                contentDescription = "stars",
                tint = color,
                imageVector = if(index<=rating){
                    Icons.Rounded.Star
                }else{
                    if(isHalfStar){
                        isHalfStar = false
                        Icons.Rounded.StarHalf
                    }else{
                        Icons.Rounded.StarOutline
                    }
                }
            )
        }
    }

}