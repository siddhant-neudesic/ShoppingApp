package com.example.shoppingapp.views

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarView(
    title:String,
    onBackNavClicked:() -> Unit = {},
    onWishListClicked:() -> Unit = {},
    onCartClicked:() -> Unit = {}
){
    val navigationIcon : (@Composable () -> Unit) =
        if(!title.contains("Shopping")){
            {
                IconButton(onClick = { onBackNavClicked()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Icon",
                        tint= Color.White
                    )
                }
            }
        }else {
            {

            }
        }


    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                color= Color.White,
                modifier = Modifier.heightIn(max = 24.dp))
            },
        colors  = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = navigationIcon,
        actions = {

            IconButton(onClick = onCartClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = null,
                    tint=Color.White
                )
            }
            IconButton(onClick = onWishListClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart_filled),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    )
}