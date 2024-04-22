package com.example.shoppingapp.data

import android.content.Context
import androidx.room.Room
import com.example.shoppingapp.repositories.CartRepository
import com.example.shoppingapp.repositories.WishListRepository

object Graph {
    private lateinit var database: WishListDatabase
    private lateinit var cartDatabase: CartDatabase
    val wishRepository by lazy {
        WishListRepository(wishlistDAO = database.wishlistDao())
    }

    val cartRepository by lazy {
        CartRepository(cartDAO = cartDatabase.cartDAO())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, WishListDatabase::class.java,"wishlist.db").build()
        cartDatabase = Room.databaseBuilder(context, CartDatabase::class.java,"cart.db").build()

    }
}