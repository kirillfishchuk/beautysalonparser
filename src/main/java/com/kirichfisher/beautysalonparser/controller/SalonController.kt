package com.kirichfisher.beautysalonparser.controller

import com.kirichfisher.beautysalonparser.dto.SalonDto
import com.kirichfisher.beautysalonparser.service.SalonCsvService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/salons")
class SalonController(
    private val salonCsvService: SalonCsvService
) {

    @GetMapping
    fun getAll(): List<SalonDto> =
        salonCsvService.getAll()

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): ResponseEntity<SalonDto> {

        val salon = salonCsvService.getById(id)
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(salon)
    }
}