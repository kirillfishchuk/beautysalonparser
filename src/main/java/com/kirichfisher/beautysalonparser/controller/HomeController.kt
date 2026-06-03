package com.kirichfisher.beautysalonparser.controller

import com.kirichfisher.beautysalonparser.service.SalonCsvService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(
    private val salonCsvService: SalonCsvService
) {

    @GetMapping("/")
    fun home(model: Model): String {

        model.addAttribute(
            "salons",
            salonCsvService.getAll()
        )

        return "index"
    }
}