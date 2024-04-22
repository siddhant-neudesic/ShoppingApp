package com.example.shoppingapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.models.Cart
import com.example.shoppingapp.utils.CartCalculations
import com.example.shoppingapp.utils.CartCalculations.round
import com.example.shoppingapp.utils.CheckOutUtils
import com.gowtham.ratingbar.RatingBar
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CheckOutScreen(
    checkoutItems:List<Cart>,
    onProceedToPay:(List<Cart>) -> Unit
){
    Scaffold(
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
                        val expanded = remember { mutableStateOf(false) }
                        val selectedItem = remember { mutableIntStateOf(CheckOutUtils.paymentOptions.first().icon) }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            NumberFormat.getCurrencyInstance(Locale.getDefault()).currency?.let {
                                Text(
                                    text = it.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                            Text(
                                text = CartCalculations.getGrandTotal(checkoutItems).toString() ,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(modifier = Modifier
                                .wrapContentWidth()
                                .padding(8.dp)){

                                   Box(
                                       modifier = Modifier.wrapContentSize().clickable {
                                           expanded.value = true
                                       }
                                   ){
                                       Row(
                                           horizontalArrangement = Arrangement.Center,
                                           verticalAlignment = Alignment.CenterVertically
                                       ) {
                                           Image(
                                               painter = painterResource(id = selectedItem.intValue),
                                               contentDescription = null,

                                           )
                                           Icon(
                                               imageVector = Icons.Default.ArrowDropDown,
                                               contentDescription = null,
                                           )
                                       }

                                }
                                DropdownMenu(
                                    expanded = expanded.value,
                                    onDismissRequest = { expanded.value = false })
                                {
                                    CheckOutUtils.paymentOptions.forEach {item ->
                                        DropdownMenuItem(
                                            modifier = Modifier
                                                .wrapContentWidth()
                                                .padding(8.dp),
                                            text= {
                                                Text(
                                                    text = item.title,
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            },
                                            onClick = {
                                                selectedItem.intValue = item.icon
                                                expanded.value = false
                                            },
                                            leadingIcon = {
                                                Image(
                                                    modifier = Modifier
                                                        .width(32.dp)
                                                        .height(32.dp),
                                                    painter = painterResource(id = item.icon),
                                                    contentDescription = null,
                                                )
                                            }
                                        )

                                    }
                                }
                            }

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
                                onProceedToPay(checkoutItems)
                            }
                        ) {
                            Text(
                                text = "Proceed To Pay",
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
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                items(checkoutItems){checkoutItem ->
                    CheckoutItem(cart = checkoutItem)
                }
            }
        }

    }
}

@Composable
fun CheckoutItem(
    cart: Cart
){
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
            Text(
                text = "Qty:${cart.qty}"
            )
        }
        Spacer(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        Column {
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