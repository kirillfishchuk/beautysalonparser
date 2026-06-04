package com.kirichfisher.beautysalonparser.service

import com.kirichfisher.beautysalonparser.entity.Salon
import com.kirichfisher.beautysalonparser.repository.SalonRepository
import org.springframework.stereotype.Service

@Service
class SalonService(
    private val repository: SalonRepository
) {

    fun getAll(): List<Salon> =
        repository.findAll()

    fun getById(id: Long): Salon =
        repository.findById(id).orElseThrow {
            RuntimeException("Salon not found")
        }

    fun update(id: Long, updated: Salon): Salon {

        val existing = getById(id)

        existing.name = updated.name
        existing.address = updated.address
        existing.district = updated.district
        existing.phone = updated.phone
        existing.website = updated.website
        existing.rating = updated.rating
        existing.reviewsCount = updated.reviewsCount
        existing.priceRange = updated.priceRange

        return repository.save(existing)
    }
}