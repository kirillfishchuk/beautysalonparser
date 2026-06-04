package com.kirichfisher.beautysalonparser.importer

import com.kirichfisher.beautysalonparser.repository.SalonRepository
import org.springframework.stereotype.Service

@Service
class GooglePlacesImporterService(

    private val searchClient: GooglePlacesClient,
    private val detailsClient: GooglePlacesDetailsClient,
    private val mapper: SalonMapper,
    private val repository: SalonRepository
) {

    fun import() {

        val target = 100

        val placeIds = searchClient.searchWarsawSalons(target)

        var saved = 0

        for (placeId in placeIds) {

            if (saved >= target) break

            val details = detailsClient.fetchDetails(placeId)
            val salon = mapper.map(details)

            val exists = repository.existsByNameAndAddress(
                salon.name,
                salon.address
            )

            if (!exists) {
                repository.save(salon)
                saved++
            }
        }

        println("Imported $saved new salons")
    }
}