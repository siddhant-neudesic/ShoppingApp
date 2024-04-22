package com.example.shoppingapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("wishlist-table")
@TypeConverters(RatingConverter::class)
data class Product(
    val category: String?="",
    val description: String?="",
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val image: String?=null,
    val price: String?="",
    val title: String?="",
    val rating: Rating?= Rating(0f,0),
    val isWishListed:Boolean=false
):Parcelable

fun Product.toCart(): Cart {
    return Cart(
        category = this.category,
        description = this.description,
        id = this.id,
        image = this.image,
        price = this.price,
        title = this.title,
        rating = this.rating,
        isWishListed = this.isWishListed,
        qty = 0
    )
}