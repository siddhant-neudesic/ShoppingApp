package com.example.shoppingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingapp.models.Cart

@Database(
    entities = [Cart::class],
    version = 1,
    exportSchema = false
)

abstract class CartDatabase:RoomDatabase() {
   abstract fun cartDAO(): CartDAO
}