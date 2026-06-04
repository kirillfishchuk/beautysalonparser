package com.kirichfisher.beautysalonparser.entity

import jakarta.persistence.*

@Entity
@Table(name = "salons")
 class Salon(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name: String,

    var address: String,

    var district: String,

    var phone: String? = null,

    var website: String? = null,

    var rating: Double? = null,

    var reviewsCount: Int? = null,

    var priceRange: String? = null
)