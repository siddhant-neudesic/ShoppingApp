package com.example.shoppingapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("cart-table")
@TypeConverters(RatingConverter::class)
data class Cart(
    val category: String?="",
    val description: String?="",
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val image: String?=null,
    val price: String?="",
    val title: String?="",
    val rating: Rating?= Rating(0f,0),
    val isWishListed:Boolean?=false,
    val qty:Int = 0
): Parcelable

