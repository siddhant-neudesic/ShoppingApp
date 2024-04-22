package com.example.shoppingapp.utils

import androidx.annotation.DrawableRes
import com.example.shoppingapp.R

object CheckOutUtils {
    data class PaymentMethod(val title:String,@DrawableRes val icon:Int)
    val paymentOptions = listOf(
        PaymentMethod("UPI",R.drawable.ic_upi),
        PaymentMethod("Cash", R.drawable.ic_cash),
        PaymentMethod("Net Banking",R.drawable.ic_net_banking)
    )
}