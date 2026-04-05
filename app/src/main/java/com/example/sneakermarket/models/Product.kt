package com.example.sneakermarket.models

data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val type: String,
    val price: Double,
    val imageUrl: String,
    val availableSizes: List<Double>,
    val color: String, // Added
    val rating: Double, // Added
    val isFeatured: Boolean = false
)