package com.example.shoppingapp.utils

import com.example.shoppingapp.models.Cart

object CartCalculations {

    fun getGrandTotal(items:List<Cart>):Float{
        var sum = 0f
        items.forEach {cart ->
            sum += (cart.price!!.toFloat() * cart.qty.toFloat()).round()
        }
        return sum.round()
    }

    fun Float.round(): Float {
        return "%.${2}f".format(this).toFloat()
    }

}