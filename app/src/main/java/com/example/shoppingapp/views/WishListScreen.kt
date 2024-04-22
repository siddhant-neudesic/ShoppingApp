package com.example.shoppingapp.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.models.Cart
import com.example.shoppingapp.models.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishListScreen(
    viewState: List<Product>,
    navigateToDetail:(Product) -> Unit,
    navigateToWishList: () -> Unit,
    navigateToCart:() -> Unit,
    toggleWishListItem: (Product) -> Unit,
    onBackNavClick:() -> Unit,
    onDeleteWish:(Product) -> Unit,
    addToCart:(Cart) -> Unit
){
    Scaffold (
        topBar = {
            AppBarView(
                title = "WishList",
                onBackNavClicked = onBackNavClick,
                onWishListClicked = navigateToWishList,
                onCartClicked = navigateToCart
            )
        }
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it) ){

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewState, key = {product -> product}){product ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {disValue ->
                            if(disValue == SwipeToDismissBoxValue.EndToStart){
                                onDeleteWish(product)
                            }
                            true
                        }
                    )
                    SwipeToDismissBox(
                        state = dismissState ,
                        backgroundContent = {
                            val color by animateColorAsState(
                                if(dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart){
                                    Color.Red
                                }else{
                                    Color.Transparent
                                }, label = ""
                            )
                            val alignment = Alignment.CenterEnd
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ){
                                Icon(imageVector = Icons.Default.Delete , contentDescription = "Delete Icon", tint = Color.White)
                            }
                        },
                        enableDismissFromStartToEnd = true,
                        content ={
                            ProductItem(product.copy(isWishListed = true),navigateToDetail,toggleWishListItem, addToCart)
                        }
                    )
                }
            }
        }
    }
}