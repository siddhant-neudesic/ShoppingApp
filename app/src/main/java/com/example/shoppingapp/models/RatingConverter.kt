package com.example.shoppingapp.models

import androidx.room.TypeConverter
import com.google.gson.Gson

class RatingConverter {
    @TypeConverter
    fun fromString(value: String): Rating {
        return Gson().fromJson(value, Rating::class.java)
    }

    @TypeConverter
    fun toString(rating: Rating): String {
        return Gson().toJson(rating)
    }
}