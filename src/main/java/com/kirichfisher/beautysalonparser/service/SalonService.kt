package com.kirichfisher.beautysalonparser.service

import com.kirichfisher.beautysalonparser.dto.SalonUpdateRequest
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
        repository.findById(id)
            .orElseThrow { RuntimeException("Salon not found") }

    fun update(id: Long, req: SalonUpdateRequest): Salon {

        val salon = repository.findById(id)
            .orElseThrow { RuntimeException("Salon not found") }

        salon.name = req.name
        salon.address = req.address
        salon.district = req.district
        salon.phone = req.phone
        salon.website = req.website
        salon.rating = req.rating
        salon.reviewsCount = req.reviewsCount
        salon.priceRange = req.priceRange

        return repository.save(salon)
    }
}