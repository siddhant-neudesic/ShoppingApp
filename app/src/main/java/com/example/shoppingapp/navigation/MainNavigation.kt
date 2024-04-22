package com.example.shoppingapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shoppingapp.models.Cart
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.viewmodels.CartViewModel
import com.example.shoppingapp.viewmodels.ProductsViewModel
import com.example.shoppingapp.viewmodels.WishListViewModel
import com.example.shoppingapp.views.CartScreen
import com.example.shoppingapp.views.CheckOutScreen
import com.example.shoppingapp.views.ProductDetailsScreen
import com.example.shoppingapp.views.ProductsScreen
import com.example.shoppingapp.views.SuccessScreen
import com.example.shoppingapp.views.WishListScreen

@Composable
fun MainNavigationGraph(navController: NavHostController){
    val productsViewModel:ProductsViewModel = viewModel(factory = ProductsViewModel.Factory)
    val viewState by productsViewModel.filteredProducts
    val searchState by productsViewModel.searchText

    val wishListViewModel:WishListViewModel = viewModel()
    val wishlistState by wishListViewModel.wishlist.collectAsState(initial = emptyList())

    val cartViewModel:CartViewModel = viewModel()
    val cartItems by cartViewModel.cartItems.collectAsState(initial = emptyList())
    
    NavHost(navController = navController, startDestination = Screen.ProductsScreen.route){
        composable(Screen.ProductsScreen.route){
            ProductsScreen(
                viewState = viewState,
                searchState = searchState,
                onSearchText = { searchText ->
                    productsViewModel.onSearch(searchText)
                },
                navigateToDetail = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("cat",it)
                    navController.navigate(Screen.DetailScreen.route)
                },
                navigateToWishList = {
                    navController.navigate(Screen.WishListScreen.route)
                },
                navigateToCart = {
                    navController.navigate(Screen.CartScreen.route)
                },
                toggleWishListItem = {product ->
                    wishListViewModel.toggleWishListItem(product)
                },
                wishListItems = wishlistState,
                addToCart = {cart ->
                    cartViewModel.addToCart(cart)
                },
                onSort = {
                    productsViewModel.sortProducts(true)
                }
            )
        }

        composable(route = Screen.DetailScreen.route){
            val product = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("cat") ?: Product()
            ProductDetailsScreen(
                product = product,
                navigateToWishList = {
                    navController.navigate(Screen.WishListScreen.route)
                },
                navigateToCart = {
                    navController.navigate(Screen.CartScreen.route)
                },
                onBackNavClick = {
                    navController.navigateUp()
                },
                toggleWishListItem = { productItem ->
                    wishListViewModel.toggleWishListItem(productItem)
                },
                wishListViewModel = wishListViewModel,
                addToCart = {cart ->
                    cartViewModel.addToCart(cart)
                },
                onCheckout = {
                    Log.d("OnBuyNow",it.toString())
                    navController.currentBackStackEntry?.savedStateHandle?.set("checkout",it)
                    navController.navigate(Screen.CheckoutScreen.route)
                }

            )
        }

        composable(route = Screen.WishListScreen.route){
            WishListScreen(
                viewState = wishlistState,
                navigateToDetail = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("cat",it)
                    navController.navigate(Screen.DetailScreen.route)
                },
                navigateToWishList = {
                    navController.navigate(Screen.WishListScreen.route)
                },
                navigateToCart = {
                    navController.navigate(Screen.CartScreen.route)
                },
                toggleWishListItem = {product ->
                    wishListViewModel.toggleWishListItem(product)
                },
                onBackNavClick = {
                    navController.navigateUp()
                },
                onDeleteWish = {product ->
                    wishListViewModel.deleteWish(product)
                },
                addToCart = {cart ->
                    cartViewModel.addToCart(cart)
                }
            )
        }

        composable(route =Screen.CartScreen.route){
            CartScreen(
                viewState = cartItems,
                navigateToWishList = {
                    navController.navigate(Screen.WishListScreen.route)
                },
                navigateToCart = {
                    navController.navigate(Screen.CartScreen.route)
                },
                onBackNavClick = {
                    navController.navigateUp()
                },
                onCartItemDelete = {cart ->
                    cartViewModel.removeFromCart(cart)
                },
                onCartItemUpdate = {cart ->
                    cartViewModel.updateCart(cart)
                },
                onCheckout = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("checkout",it)
                    navController.navigate(Screen.CheckoutScreen.route)
                }
            )
        }

        composable(route= Screen.CheckoutScreen.route){
            val checkOutItems = navController.previousBackStackEntry?.savedStateHandle?.get<List<Cart>>("checkout") ?: emptyList()
            CheckOutScreen(
                checkoutItems = checkOutItems,
                onProceedToPay = {cartItems ->
                    cartViewModel.deleteMultipleCartItems(cartItems)
                    navController.navigate(Screen.SuccessScreen.route)
                }
            )
        }

        composable(route=Screen.SuccessScreen.route){
            SuccessScreen(navigateToHome = {
                navController.navigate(Screen.ProductsScreen.route)
            })
        }
    }
}