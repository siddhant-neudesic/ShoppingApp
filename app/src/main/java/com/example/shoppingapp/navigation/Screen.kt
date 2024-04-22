package com.example.shoppingapp.navigation

sealed class Screen(val route:String) {
    data object ProductsScreen:Screen("productsScreen")
    data object DetailScreen:Screen("detailsScreen")
    data object WishListScreen:Screen("wishListScreen")
    data object CartScreen:Screen("cartScreen")
    data object CheckoutScreen:Screen("checkoutScreen")
    data object SuccessScreen:Screen("successScreen")
}
