package com.example.shoppingapp.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.R
import com.example.shoppingapp.models.Cart
import com.example.shoppingapp.utils.CartCalculations
import com.example.shoppingapp.utils.CartCalculations.round
import com.gowtham.ratingbar.RatingBar
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CartScreen(
    viewState:List<Cart>,
    navigateToWishList: () -> Unit,
    navigateToCart:() -> Unit,
    onBackNavClick:() -> Unit,
    onCartItemUpdate:(Cart) -> Unit,
    onCartItemDelete:(Cart) -> Unit,
    onCheckout:(List<Cart>) -> Unit
){
    Scaffold (
        topBar = {
            AppBarView(
                title = "Cart",
                onBackNavClicked = onBackNavClick,
                onWishListClicked = navigateToWishList,
                onCartClicked = navigateToCart
            )
        },
        bottomBar = {
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
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text(
                                text = "Grand Total:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                            NumberFormat.getCurrencyInstance(Locale.getDefault()).currency?.let {
                                Text(
                                    text = it.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                            Text(
                                text = CartCalculations.getGrandTotal(viewState).toString() ,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .background(MaterialTheme.colorScheme.primary),
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.background,
                                disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                                disabledContentColor = MaterialTheme.colorScheme.background
                            ),
                            onClick = {
                                onCheckout(viewState)
                            }
                        ) {
                            Text(
                                text = "Checkout",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold ,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }
                }
            )
        }
    ){
       Column(
           modifier= Modifier.padding(it)
       ) {
           LazyColumn(
               modifier = Modifier.padding(16.dp)
           ) {
               items(viewState){cart ->
                   CartItem(
                       cart = cart,
                       onCartItemUpdate=onCartItemUpdate,
                       onCartItemDelete=onCartItemDelete
                   )
               }
           }
       }
    }

}


@Composable
fun CartItem(
    cart: Cart,
    onCartItemUpdate:(Cart) -> Unit,
    onCartItemDelete:(Cart) -> Unit,
){
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(cart.image),
                contentDescription = "Image for ${cart.title}",
                modifier = Modifier
                    .width(120.dp)
                    .height(140.dp)
            )
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.padding(0.dp),
                    onClick = {
                        if(cart.qty == 1){
                            Toast.makeText(context,"Quantity cannot be less than 1",Toast.LENGTH_LONG).show()
                        }else{
                            onCartItemUpdate(cart.copy(qty = cart.qty - 1))
                        }
                    }
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_remove), contentDescription =null)
                }
                Text(
                    text = "Qty:${cart.qty}"
                )
                IconButton(
                    modifier = Modifier.padding(0.dp),
                    onClick = {
                        onCartItemUpdate(cart.copy(qty = cart.qty + 1))
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription =null )
                }
            }
        }
        Spacer(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(
                    onClick = {
                       onCartItemDelete(cart)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Text(
                text =cart.title!!,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(top = 8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                RatingBar(value = cart.rating!!.rate, onValueChange = {}, onRatingChanged = {})
                Spacer(Modifier.padding(start = 8.dp))
                Text(
                    text = "(${cart.rating.count})",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Row{
                NumberFormat.getCurrencyInstance(Locale.getDefault()).currency?.let { Text(text = it.toString() ) }
                Spacer(Modifier.padding(start = 2.dp))
                Text(
                    text = cart.price!!,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.padding(start = 2.dp))
                Text(
                    text = "x",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Spacer(Modifier.padding(start = 2.dp))
                Text(
                    text = cart.qty.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.padding(start = 2.dp))
                Text(
                    text = "=",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Spacer(Modifier.padding(start = 2.dp))
                Text(
                    text = (cart.price.toFloat() * cart.qty.toFloat()).round().toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
        }
    }
    HorizontalDivider(thickness = 1.dp)
}

