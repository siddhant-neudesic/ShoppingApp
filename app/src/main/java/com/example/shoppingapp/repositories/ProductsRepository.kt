package com.example.shoppingapp.repositories

import com.example.shoppingapp.api.ProductsAPIService
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.ProductsResponse
import com.example.shoppingapp.models.Result

interface IProductsRepository{

    suspend fun getAllProducts():Result<ProductsResponse>

    suspend fun getProductById(id:Int):Result<Product>
}

class ProductsRepository(private val productsAPIService: ProductsAPIService):IProductsRepository {

    override suspend fun getAllProducts():Result<ProductsResponse> {
        return try {
            val result = productsAPIService.getProducts()
            Result.Success(data = result)
        }catch (e:Exception){
            Result.Error(exception = e.message)
        }
    }

    override suspend fun getProductById(id: Int): Result<Product>{
        return try {
            val result = productsAPIService.getProduct(id)
            Result.Success(data = result)
        }catch (e:Exception){
            Result.Error(exception = e.message)
        }
    }
}