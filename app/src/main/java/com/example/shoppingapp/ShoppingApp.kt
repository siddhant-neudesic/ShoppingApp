package com.example.shoppingapp

import android.app.Application
import com.example.shoppingapp.data.Graph

class ShoppingApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}