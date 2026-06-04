package com.kirichfisher.beautysalonparser.controller

import com.kirichfisher.beautysalonparser.entity.Salon
import com.kirichfisher.beautysalonparser.service.SalonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/salons")
class SalonController(
    private val service: SalonService
) {

    @GetMapping
    fun getAll(): List<Salon> =
        service.getAll()

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): Salon =
        service.getById(id)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody salon: Salon
    ): Salon =
        service.update(id, salon)
}