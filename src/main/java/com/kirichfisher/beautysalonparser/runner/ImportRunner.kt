package com.kirichfisher.beautysalonparser.runner

import com.kirichfisher.beautysalonparser.importer.GooglePlacesImporterService
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ImportRunner(
    private val importer: GooglePlacesImporterService
) {

    @EventListener(ApplicationReadyEvent::class)
    fun run() {
        importer.import()
    }
}