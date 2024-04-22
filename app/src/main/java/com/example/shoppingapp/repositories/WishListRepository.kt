package com.example.shoppingapp.repositories

import com.example.shoppingapp.data.WishListDAO
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.Result
import kotlinx.coroutines.flow.Flow

interface IWishListRepository{
    suspend fun addWish(wish: Product)

    fun getAllWishes():Flow<List<Product>>

    fun getWishById(id:Int):Flow<Product>

    suspend fun updateWish(wish: Product):Result<Product>

    suspend fun deleteWish(wish: Product):Result<Product>

}

class WishListRepository(private val wishlistDAO: WishListDAO) : IWishListRepository {

    override suspend fun addWish(wish: Product){
        wishlistDAO.addWish(wish)
    }

    override fun getAllWishes() : Flow<List<Product>> = wishlistDAO.getAllWishes()

    override fun getWishById(id:Int) : Flow<Product> =  wishlistDAO.getWishById(id)

    override suspend fun updateWish(wish: Product):Result<Product>{
        return try {
            wishlistDAO.updateWish(wish)
            Result.Success(wish)
        }catch (e:Exception){
            Result.Error(exception = e.message)
        }

    }

    override suspend fun deleteWish(wish: Product):Result<Product>{
        return try {
            wishlistDAO.deleteWish(wish)
            Result.Success(wish)
        }catch (e:Exception){
            Result.Error(exception = e.message)
        }

    }
}