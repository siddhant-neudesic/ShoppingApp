package com.example.shoppingapp.repositories

import com.example.shoppingapp.data.CartDAO
import com.example.shoppingapp.models.Cart
import kotlinx.coroutines.flow.Flow

interface ICartRepository{
    suspend fun addToCart(cart: Cart)

    suspend fun removeFromCart(cart: Cart)

    fun getAllCartItems():Flow<List<Cart>>

    suspend fun updateCart(cart: Cart)

    fun getCartItem(id:Int):Flow<Cart>
}

class CartRepository(private val cartDAO: CartDAO):ICartRepository {
    override suspend fun addToCart(cart: Cart) {
        cartDAO.addToCart(cart)
    }

    override suspend fun removeFromCart(cart: Cart) {
        cartDAO.deleteCartItems(cart)
    }

    override fun getAllCartItems(): Flow<List<Cart>> {
        return cartDAO.getAllItemsInCart()
    }

    override suspend fun updateCart(cart: Cart) {
        cartDAO.updateCartItems(cart)
    }

    override fun getCartItem(id: Int): Flow<Cart> {
        return cartDAO.getCartItem(id)
    }
}