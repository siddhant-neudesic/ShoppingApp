package com.example.shoppingapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R

@Composable
fun BottomAppBarView(
    isWishListed:Boolean,
    toggleWishListItem:() -> Unit,
    addToCart:() -> Unit,
    onBuyNow:() -> Unit
){
    BottomAppBar(
        modifier = Modifier
            .height(70.dp)
            .padding(4.dp),
        containerColor = MaterialTheme.colorScheme.background,
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = addToCart,
                    modifier = Modifier
                        .weight(0.3f)
                        .height(24.dp)
                        .background(MaterialTheme.colorScheme.background)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(10)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_to_cart),
                        contentDescription =null
                    )
                }
                Spacer(Modifier.padding(start = 6.dp))
                Button(
                    modifier = Modifier
                        .weight(1.3f)
                        .padding(horizontal = 8.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10)
                        ),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                        disabledContentColor = MaterialTheme.colorScheme.background
                    ),
                        onClick = toggleWishListItem
                ) {
                    Text(
                        text = if(isWishListed) "Remove From Wishlist"  else "Add To Wishlist",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold ,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Spacer(Modifier.padding(start = 6.dp))

                Button(
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(horizontal = 8.dp)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10)
                        ),

                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                        disabledContentColor = MaterialTheme.colorScheme.background
                    ),
                    onClick = onBuyNow
                ) {
                    Text(
                        text = "Buy Now",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    )
}