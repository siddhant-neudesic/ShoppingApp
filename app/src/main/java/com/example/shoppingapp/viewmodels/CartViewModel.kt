package com.example.shoppingapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.Graph
import com.example.shoppingapp.models.Cart
import com.example.shoppingapp.repositories.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CartViewModel(
    private val cartRepository: CartRepository = Graph.cartRepository
) :ViewModel() {
    lateinit var cartItems: Flow<List<Cart>>

    init {
        getAllCartItems()
    }

    private fun getAllCartItems(){
        viewModelScope.launch {
            val result = cartRepository.getAllCartItems()
            cartItems = result
        }
    }

    fun addToCart(cart: Cart){
        viewModelScope.launch(Dispatchers.IO) {
            if(cart.id != null){
                val result = getCartItem(cart.id)
                if(result != null){
                    updateCart(cart.copy(qty = result.qty+1))
                }else{
                    cartRepository.addToCart(cart.copy(qty = 1))
                }

            }

        }
    }

    fun removeFromCart(cart: Cart){
        viewModelScope.launch (Dispatchers.IO){
            cartRepository.removeFromCart(cart)
        }
    }

    fun updateCart(cart: Cart){
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.updateCart(cart)
        }
    }

    private fun getCartItem(id:Int):Cart? = runBlocking {
        cartRepository.getCartItem(id).firstOrNull()
    }

    fun deleteMultipleCartItems(cartItems:List<Cart>){
        cartItems.forEach {cartItem ->
            if(cartItem.id != null){
                val result = getCartItem(cartItem.id)
                if(result != null){
                    removeFromCart(cartItem)
                }
            }
        }
    }
}