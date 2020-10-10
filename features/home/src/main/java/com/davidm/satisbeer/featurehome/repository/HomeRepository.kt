package com.davidm.satisbeer.featurehome.repository

import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.repository.network.HomeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeApi: HomeApi
) {

    /**
     * This will retrieve a paged list of all the available beers
     */
    suspend fun retrieveBeers(
        pageSize: Int,
        page: Int,
        beerName: String? = null
    ): List<Beer> {
        return withContext(Dispatchers.IO) {
            return@withContext homeApi.getBeers(beerName, page, pageSize)
        }
    }
}