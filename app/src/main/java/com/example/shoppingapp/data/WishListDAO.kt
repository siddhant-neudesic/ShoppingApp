package com.example.shoppingapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppingapp.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishListDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWish(product: Product)

    @Query("SELECT * FROM `wishlist-table`")
    abstract fun getAllWishes(): Flow<List<Product>>

    @Update
    abstract suspend fun updateWish(wishEntity: Product)

    @Delete
    abstract suspend fun deleteWish(wishEntity: Product)

    @Query("SELECT * FROM `wishlist-table` where id=:id")
    abstract fun getWishById(id:Int): Flow<Product>

}