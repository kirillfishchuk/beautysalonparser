package com.kirichfisher.beautysalonparser.repository

import com.kirichfisher.beautysalonparser.entity.Salon
import org.springframework.data.jpa.repository.JpaRepository

interface SalonRepository : JpaRepository<Salon, Long> {

    fun findByDistrictContainingIgnoreCase(
        district: String
    ): List<Salon>
}