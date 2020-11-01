package com.davidm.satisbeer.featurehome.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Beer(
    @Json(name = "id") val id: Long,
    @Json(name = "abv") val abv: Double,
    @Json(name = "brewers_tips") val brewersTips: String,
    @Json(name = "contributed_by") val contributedBy: String,
    @Json(name = "description") val description: String,
    @Json(name = "first_brewed") val firstBrewed: String,
    @Json(name = "food_pairing") val foodPairing: List<String>,
    @Json(name = "image_url") val imageUrl: String? = null,
    @Json(name = "name") val name: String,
    @Json(name = "tagline") val tagLine: String,
) : Parcelable

fun convertFoodPairingList(foodPairing: List<String>): String {
    return foodPairing.joinToString(separator = "\n - ", prefix = " - ")
}

fun generateChipList(): List<String> {
    val lagerBeer = "Lager"
    val blondeBeer = "Blonde"
    val maltsBeer = "Malts"
    val stoutsPortersBeer = "Stouts"
    val ipaBeer = "Ipa"

    return listOf(lagerBeer, blondeBeer, maltsBeer, stoutsPortersBeer, ipaBeer)

}