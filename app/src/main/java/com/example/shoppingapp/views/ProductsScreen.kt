package com.example.shoppingapp.views


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.R
import com.example.shoppingapp.models.Cart
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.ProductsState
import com.example.shoppingapp.models.toCart
import com.gowtham.ratingbar.RatingBar
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductsScreen(
    viewState:ProductsState,
    navigateToDetail:(Product) -> Unit,
    navigateToWishList: () -> Unit,
    navigateToCart:() -> Unit,
    toggleWishListItem: (Product) -> Unit,
    wishListItems:List<Product>,
    onSearchText:(String) -> Unit,
    searchState:String,
    addToCart:(Cart) -> Unit,
    onSort:() -> Unit
) {
    Scaffold (
        topBar = {
            AppBarView(
                title = "Shopping App",
                onWishListClicked = navigateToWishList,
                onCartClicked =navigateToCart
            )
        }
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it) ){
            when{
                viewState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                viewState.error != null  -> {
                    Text(text = viewState.error)
                }
                else -> {
                    Column(
                        modifier= Modifier
                            .fillMaxSize()
                            .padding(0.dp)
                    ){
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                leadingIcon = {
                                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon" )
                                },
                                modifier = Modifier
                                    .weight(0.9f)
                                    .padding(0.dp),
                                label = {
                                    Text(text = "Search...")
                                },
                                value = searchState,
                                onValueChange = {text ->
                                    onSearchText(text)
                                }
                            )
                            IconButton(onClick = onSort) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_sort),
                                    contentDescription = null,
                                    modifier= Modifier.size(24.dp)
                                )
                            }
                        }

                        ProductScreen(viewState.list,navigateToDetail,toggleWishListItem,wishListItems,addToCart)
                    }
                }
            }
        }
    }

}

@Composable
fun  ProductScreen(
    products:List<Product>,
    navigateToDetail:(Product) -> Unit,
    toggleWishListItem: (Product) -> Unit,
    wishListItems: List<Product>,
    addToCart:(Cart) -> Unit
){
    Column(
        modifier= Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {

        LazyVerticalGrid(GridCells.Fixed(1), modifier = Modifier.fillMaxSize()) {
            items(products){product ->
                val isAddedToWishlist = wishListItems.contains(product)
                ProductItem(product.copy(isWishListed = isAddedToWishlist),navigateToDetail,toggleWishListItem,addToCart)
            }
        }
    }

}

@Composable
fun ProductItem(
    product: Product,
    navigateToDetail:(Product) -> Unit,
    toggleWishListItem: (Product) -> Unit,
    addToCart:(Cart) -> Unit
){
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable {
                navigateToDetail(product)
            },
        ) {

        Image(
            painter = rememberAsyncImagePainter(product.image),
            contentDescription = "Image for ${product.title}",
            modifier = Modifier
                .width(120.dp)
                .height(140.dp)
        )
        Spacer(modifier = Modifier.padding(start = 8.dp, end = 8.dp))
        Column {
            Text(
                text =product.title!!,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
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
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Row{
                NumberFormat.getCurrencyInstance(Locale.getDefault()).currency?.let { Text(text = it.toString() ) }
                Spacer(Modifier.padding(start = 2.dp))
                Text(
                    text = product.price!!,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))

            Row {
                IconButton(
                    onClick = {
                        toggleWishListItem(product)
                    },
                    modifier = Modifier
                        .width(30.dp)
                        .height(24.dp),
                ) {
                    Icon(
                        painter = painterResource(id = if(product.isWishListed) R.drawable.ic_heart_red else R.drawable.ic_heart_outlined),
                        contentDescription =null,
                        tint = if(product.isWishListed) Color.Red else MaterialTheme.colorScheme.onBackground

                    )
                }

                Spacer(Modifier.padding(start = 16.dp))
                IconButton(
                    onClick = {
                        addToCart(product.toCart())
                    },
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_to_cart),
                        contentDescription =null
                    )
                }

            }
        }
        Spacer(modifier = Modifier.padding(start = 8.dp, end = 8.dp))
    }
    HorizontalDivider(thickness = 1.dp)
}