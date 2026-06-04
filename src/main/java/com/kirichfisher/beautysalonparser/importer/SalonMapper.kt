package com.kirichfisher.beautysalonparser.importer

import com.kirichfisher.beautysalonparser.entity.Salon
import org.json.JSONObject
import org.springframework.stereotype.Component

@Component
class SalonMapper {

    fun map(json: JSONObject): Salon {

        val address = json.optString("formatted_address")

        return Salon(
            name = json.optString("name"),
            address = address,
            district = extractDistrict(address),
            phone = json.optString("formatted_phone_number").takeIf { it.isNotBlank() },
            website = json.optString("website").takeIf { it.isNotBlank() },
            rating = json.optDouble("rating", 0.0).takeIf { it > 0 },
            reviewsCount = json.optInt("user_ratings_total", 0).takeIf { it > 0 },
            priceRange = mapPrice(json.optInt("price_level", -1))
        )
    }

    private fun extractDistrict(address: String): String {
        return address.split(",")
            .map { it.trim() }
            .firstOrNull { it.contains("Warsaw", ignoreCase = true).not() }
            ?: "Warsaw"
    }

    private fun mapPrice(level: Int): String? {
        return when (level) {
            0, 1 -> "$"
            2 -> "$$"
            3 -> "$$$"
            4 -> "$$$$"
            else -> null
        }
    }
}