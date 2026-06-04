package com.kirichfisher.beautysalonparser.importer

import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GooglePlacesDetailsClient(
    @Value("\${google.places.api-key}") private val apiKey: String,
    private val restTemplate: RestTemplate
) {

    fun fetchDetails(placeId: String): JSONObject {

        val url = """
            https://maps.googleapis.com/maps/api/place/details/json
            ?place_id=$placeId
            &fields=name,formatted_address,formatted_phone_number,website,rating,user_ratings_total,price_level,address_components
            &key=$apiKey
        """.trimIndent().replace("\n", "")

        val response = restTemplate.getForObject(url, String::class.java)

        return JSONObject(response)
            .getJSONObject("result")
    }
}