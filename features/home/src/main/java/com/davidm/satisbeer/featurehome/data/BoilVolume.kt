package com.davidm.satisbeer.featurehome.data

import com.squareup.moshi.Json

data class BoilVolume(
    @Json(name = "unit") val unit: String,
    @Json(name = "value") val value: Int
)