package com.davidm.satisbeer.featurehome.data

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: listOf(),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeDouble(abv)
        parcel.writeString(brewersTips)
        parcel.writeString(contributedBy)
        parcel.writeString(description)
        parcel.writeString(firstBrewed)
        parcel.writeStringList(foodPairing)
        parcel.writeString(imageUrl)
        parcel.writeString(name)
        parcel.writeString(tagLine)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Beer> {
        override fun createFromParcel(parcel: Parcel): Beer {
            return Beer(parcel)
        }

        override fun newArray(size: Int): Array<Beer?> {
            return arrayOfNulls(size)
        }
    }
}

fun convertFoodPairingList(foodPairing: List<String>): String {
    var foodPairingList = ""
    foodPairing.forEach {
        foodPairingList = "$foodPairingList - $it \n"
    }
    return foodPairingList
}

fun getDummyBeer(): Beer {
    return Beer(
        -1, 0.0, "", "", "", "", emptyList(), "", "", ""
    )
}