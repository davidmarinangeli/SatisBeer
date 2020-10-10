package com.davidm.satisbeer.featurehome.repository

import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.network.HomeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeApi: HomeApi
) {

    /**
     * This method will retrieve a paged list of beers
     */

    suspend fun retrieveBeers(
        page: Int,
    ): List<Beer> {

        return withContext(Dispatchers.IO) {
            return@withContext homeApi.getPagedBeerList(page)
        }

    }
}