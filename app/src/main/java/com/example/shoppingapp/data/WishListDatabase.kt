package com.example.shoppingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppingapp.models.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)


abstract class WishListDatabase:RoomDatabase() {
    abstract fun wishlistDao():WishListDAO
}