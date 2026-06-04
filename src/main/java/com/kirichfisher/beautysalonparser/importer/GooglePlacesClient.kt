package com.kirichfisher.beautysalonparser.importer

import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class GooglePlacesClient(
    @Value("\${google.places.api-key}") private val apiKey: String,
    @Value("\${google.places.base-url}") private val baseUrl: String,
    private val restTemplate: RestTemplate
) {

    fun searchWarsawSalons(target: Int): List<String> {

        val districts = listOf(
            "Centrum", "Mokotow", "Wola",
            "Praga", "Ursynow", "Wilanow", "Zoliborz"
        )

        val placeIds = mutableSetOf<String>()

        for (district in districts) {
            if (placeIds.size >= target) break

            val query = "beauty salon Warsaw $district"
            var nextPageToken: String? = null

            do {
                val url = if (nextPageToken == null) {
                    buildInitialUrl(query)
                } else {
                    buildNextPageUrl(nextPageToken)
                }

                println("Request URL: $url")

                try {
                    val response = restTemplate.getForObject(url, String::class.java)
                        ?: break

                    val json = JSONObject(response)

                    val status = json.optString("status")
                    if (status != "OK" && status != "ZERO_RESULTS") {
                        println("Google API error status: $status")
                        println("Response: $json")
                        break
                    }

                    val results = json.optJSONArray("results") ?: break

                    for (i in 0 until results.length()) {
                        val placeId = results.getJSONObject(i).optString("place_id")

                        if (placeId.isNotBlank()) {
                            placeIds.add(placeId)
                        }

                        if (placeIds.size >= target) {
                            return placeIds.toList()
                        }
                    }

                    nextPageToken = json.optString("next_page_token")
                        .takeIf { it.isNotBlank() }

                    if (nextPageToken != null) {
                        Thread.sleep(2500) // REQUIRED by Google
                    }

                } catch (e: Exception) {
                    println("Error in district $district: ${e.message}")
                    break
                }

            } while (nextPageToken != null)
        }

        return placeIds.toList()
    }

    private fun buildInitialUrl(query: String): String {
        val encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8)
        return "$baseUrl?query=$encodedQuery&key=$apiKey"
    }

    private fun buildNextPageUrl(token: String): String {
        return "$baseUrl?pagetoken=$token&key=$apiKey"
    }
}