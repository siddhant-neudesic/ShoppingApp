package com.example.shoppingapp.api

import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsAPIService {
    @GET("products")
    suspend fun getProducts(): ProductsResponse

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id:Int):Product
}