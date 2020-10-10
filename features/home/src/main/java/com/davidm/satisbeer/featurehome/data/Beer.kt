package com.davidm.satisbeer.featurehome.data

import com.squareup.moshi.Json

data class Beer(
    val abv: Double,
    val boil_volume: BoilVolume,
    val brewers_tips: String,
    val contributed_by: String,
    val description: String,
    val first_brewed: String,
    val food_pairing: List<String>,
    val id: Int,

    @Json(name = "image_url")
    val imageUrl: String,

    val name: String,
    @Json(name = "tagline")
    val tagLine: String,
)