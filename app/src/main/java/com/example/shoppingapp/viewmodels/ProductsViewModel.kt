package com.example.shoppingapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.api.RetrofitClient
import com.example.shoppingapp.models.ProductState
import com.example.shoppingapp.models.ProductsState
import com.example.shoppingapp.models.Result
import com.example.shoppingapp.repositories.ProductsRepository
import kotlinx.coroutines.launch

class ProductsViewModel(private val productsRepository: ProductsRepository):ViewModel() {
    private val _products = mutableStateOf(ProductsState())

    private val _filteredProducts = mutableStateOf(ProductsState())
    val filteredProducts: State<ProductsState> = _filteredProducts

    private val _product = mutableStateOf(ProductState())
    val product: State<ProductState> = _product

    private val _sortOrder = mutableStateOf("DESC")

    private val _searchText = mutableStateOf("")
    val searchText:State<String> = _searchText

    init {
        getProducts()
    }


    fun onSearch(searchText:String){
        _searchText.value = searchText
        if(_searchText.value === ""){
            _filteredProducts.value = _products.value
        }else{
            val filteredList = _products.value.list.filter {product ->
                product.title!!.contains(_searchText.value,ignoreCase = true)
            }
            _filteredProducts.value  = ProductsState(
                loading = false,
                list = filteredList,
                error = null
            )
            sortProducts(false)
        }
    }

    fun sortProducts(isSortToBeUpdated:Boolean){
        if(isSortToBeUpdated){
            _sortOrder.value = if(_sortOrder.value == "ASC") "DESC" else "ASC"
        }
        val filteredProducts =  if (_sortOrder.value == "ASC") {
            filteredProducts.value.list.sortedBy { it.price?.toDoubleOrNull() ?: Double.MAX_VALUE }
        } else {
            filteredProducts.value.list.sortedByDescending { it.price?.toDoubleOrNull() ?: Double.MIN_VALUE }
        }
        _filteredProducts.value  = ProductsState(
            loading = false,
            list = filteredProducts,
            error = null
        )
    }

    private fun getProducts() {
        viewModelScope.launch {
            when (val result = productsRepository.getAllProducts()) {
                is Result.Success -> {
                    _products.value = ProductsState(
                        loading = false,
                        list = result.data,
                        error = null
                    )
                    _filteredProducts.value = ProductsState(
                        loading = false,
                        list = result.data,
                        error = null
                    )
                    sortProducts(false)
                }

                is Result.Error -> {
                    _products.value = ProductsState(
                        loading = false,
                        list = emptyList(),
                        error = result.exception
                    )
                    _filteredProducts.value = ProductsState(
                        loading = false,
                        list = emptyList(),
                        error = result.exception
                    )
                }
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory =object :ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val productsRepository = ProductsRepository(RetrofitClient.create())
                return ProductsViewModel(
                    productsRepository = productsRepository
                ) as T
            }
        }
    }
}