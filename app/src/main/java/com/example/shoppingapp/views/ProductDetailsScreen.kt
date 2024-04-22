package com.example.shoppingapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.models.Cart
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.toCart
import com.example.shoppingapp.viewmodels.WishListViewModel
import com.gowtham.ratingbar.RatingBar
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductDetailsScreen(
    product: Product,
    toggleWishListItem:(Product) -> Unit,
    wishListViewModel: WishListViewModel,
    addToCart:(Cart) -> Unit,
    navigateToWishList: () -> Unit,
    navigateToCart:() -> Unit,
    onBackNavClick:() -> Unit,
    onCheckout:(List<Cart>) -> Unit
){
    product.id?.let { wishListViewModel.getWishByIdAsync(it) }
    val wishlistItem by wishListViewModel.wishlistItem.collectAsState(initial = Product())
    Scaffold(
        topBar = {
            AppBarView(
                title = product.title!!,
                onBackNavClicked = onBackNavClick,
                onWishListClicked = navigateToWishList,
                onCartClicked = navigateToCart
            )
        },
        bottomBar = {
            BottomAppBarView(
                isWishListed = wishlistItem?.id != null ,
                toggleWishListItem = {
                    toggleWishListItem(product)
                },
                addToCart= {
                    addToCart(product.toCart())
                },
                onBuyNow = {
                    val checkoutItem = mutableListOf<Cart>()
                    checkoutItem.add(product.toCart().copy(qty = 1))
                    onCheckout(checkoutItem)
                }
            )
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = "Image for ${product.title}",
                    modifier = Modifier
                        .wrapContentSize()
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = product.title!!,
                    textAlign =  TextAlign.Start,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    RatingBar(value = product.rating!!.rate, onValueChange = {}, onRatingChanged = {})
                    Spacer(Modifier.padding(start = 8.dp))
                    Text(
                        text = "(${product.rating.count})",
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Row{
                    NumberFormat.getCurrencyInstance(Locale.getDefault()).currency?.let {currency -> Text(text = currency.toString() ) }
                    Spacer(Modifier.padding(start = 2.dp))
                    Text(
                        text = product.price!!,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = product.description!!,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }

}