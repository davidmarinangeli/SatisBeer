package com.davidm.satisbeer.featurehome.repository.network

import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.network.API_VERSION
import com.davidm.satisbeer.network.BASE_URL
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HomeApi {

    @GET("$API_VERSION/beers")
    suspend fun getPagedBeerList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Header("Accept") value: String = "application/json"
    ): List<Beer>
}