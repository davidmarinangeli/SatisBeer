package com.davidm.satisbeer.featurehome.data

import com.squareup.moshi.Json

data class Beer(
    @Json(name = "id") val id: Long,
    @Json(name = "abv") val abv: Double,
    @Json(name = "boil_volume") val boilVolume: BoilVolume,
    @Json(name = "brewers_tips") val brewersTips: String,
    @Json(name = "contributed_by") val contributedBy: String,
    @Json(name = "description") val description: String,
    @Json(name = "first_brewed") val firstBrewed: String,
    @Json(name = "food_pairing") val foodPairing: List<String>,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "name") val name: String,
    @Json(name = "tagline") val tagLine: String,
)