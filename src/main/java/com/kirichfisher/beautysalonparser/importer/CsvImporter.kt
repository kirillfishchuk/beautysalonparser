package com.kirichfisher.beautysalonparser.importer

import com.kirichfisher.beautysalonparser.entity.Salon
import com.kirichfisher.beautysalonparser.repository.SalonRepository
import org.apache.commons.csv.CSVFormat
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class CsvImporter(
    private val repository: SalonRepository
) : CommandLineRunner {

    override fun run(vararg args: String) {
        if (repository.count() > 0) {
            return
        }

        val resource =
            ClassPathResource("data/salons.csv")

        resource.inputStream.bufferedReader().use { reader ->

            val records = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get()
                .parse(reader)

            records.forEach {

                repository.save(
                    Salon(
                        name = it["name"],
                        address = it["address"],
                        district = it["district"],
                        phone = it["phone"],
                        website = it["website"],
                        rating = it["rating"].toDouble(),
                        reviewsCount = it["reviewsCount"].toInt(),
                        priceRange = it["priceRange"]
                    )
                )
            }
        }

        println("Imported ${repository.count()} salons")
    }
}