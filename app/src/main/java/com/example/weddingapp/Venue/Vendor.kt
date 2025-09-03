package com.example.weddingapp.Venue

data class Vendor(
    val name: String,
    val location: String,
    val priceRange: String,
    val minPrice: Int,
    val maxPrice: Int,
    val capacity: Int
)