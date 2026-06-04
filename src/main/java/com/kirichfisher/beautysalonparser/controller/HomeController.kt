package com.kirichfisher.beautysalonparser.controller

import com.kirichfisher.beautysalonparser.service.SalonService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(
    private val salonService: SalonService
) {

    @GetMapping("/")
    fun home(model: Model): String {

        model.addAttribute(
            "salons",
            salonService.getAll()
        )

        return "index"
    }
}