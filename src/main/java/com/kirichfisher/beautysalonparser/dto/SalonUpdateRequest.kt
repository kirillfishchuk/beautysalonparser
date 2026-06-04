package com.kirichfisher.beautysalonparser.dto

data class SalonUpdateRequest(
    val name: String,
    val address: String,
    val district: String,
    val phone: String?,
    val website: String?,
    val rating: Double?,
    val reviewsCount: Int?,
    val priceRange: String?
)