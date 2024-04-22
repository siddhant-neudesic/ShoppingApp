package com.example.shoppingapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppingapp.models.Cart
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CartDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addToCart(item: Cart)

    @Query("SELECT * FROM `cart-table`")
    abstract fun getAllItemsInCart(): Flow<List<Cart>>

    @Update
    abstract suspend fun updateCartItems(item: Cart)

    @Delete
    abstract suspend fun deleteCartItems(item: Cart)

    @Query("SELECT * FROM `cart-table` where id=:id")
    abstract fun getCartItem(id:Int): Flow<Cart>
}