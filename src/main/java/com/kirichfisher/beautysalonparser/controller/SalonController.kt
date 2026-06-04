package com.kirichfisher.beautysalonparser.controller

import com.kirichfisher.beautysalonparser.dto.SalonUpdateRequest
import com.kirichfisher.beautysalonparser.service.SalonService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/salons")
class SalonController(
    private val salonService: SalonService
) {

    @GetMapping
    fun getAll() =
        salonService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        salonService.getById(id)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody req: SalonUpdateRequest
    ) = salonService.update(id, req)
}