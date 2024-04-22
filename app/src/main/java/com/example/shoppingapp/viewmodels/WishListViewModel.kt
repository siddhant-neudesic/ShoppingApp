package com.example.shoppingapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.data.Graph
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.repositories.WishListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WishListViewModel(
    private val wishRepository: WishListRepository = Graph.wishRepository
): ViewModel() {


    lateinit var wishlist: Flow<List<Product>>
    lateinit var wishlistItem: Flow<Product?>

    init {
       getAllWishlist()
    }

    private fun getAllWishlist(){
        viewModelScope.launch {
            val result = wishRepository.getAllWishes()
            wishlist = result
        }
    }

    fun toggleWishListItem(wish: Product){
        viewModelScope.launch(Dispatchers.IO) {
            if(wish.id != null){
                val result = getWishById(wish.id)
                if(result != null){
                    wishRepository.deleteWish(wish)
                }else{
                    wishRepository.addWish(wish)
                }
            }
        }
    }

    private fun getWishById(id:Int):Product?=runBlocking {
         wishRepository.getWishById(id).firstOrNull()
    }

    fun getWishByIdAsync(id:Int){
        wishlistItem = wishRepository.getWishById(id)
    }

    fun deleteWish(wish: Product){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteWish(wish)
            getAllWishlist()
        }
    }

}