package com.kirichfisher.beautysalonparser.service

import com.kirichfisher.beautysalonparser.dto.SalonDto
import org.apache.commons.csv.CSVFormat
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class SalonCsvService {

    private val salons: List<SalonDto>

    init {

        val resource =
            ClassPathResource("data/salons.csv")

        resource.inputStream.bufferedReader().use { reader ->

            val records = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get()
                .parse(reader)

            salons = records.map {

                SalonDto(
                    id = it["id"].toLong(),
                    name = it["name"],
                    address = it["address"],
                    district = it["district"],
                    phone = it["phone"],
                    website = it["website"],
                    rating = it["rating"].toDouble(),
                    reviewsCount = it["reviewsCount"].toInt(),
                    priceRange = it["priceRange"]
                )
            }
        }
    }

    fun getAll() = salons

    fun getById(id: Long) =
        salons.find { it.id == id }
}