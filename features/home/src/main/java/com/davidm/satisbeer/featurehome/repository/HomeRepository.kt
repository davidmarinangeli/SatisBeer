package com.davidm.satisbeer.featurehome.repository

import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.repository.network.HomeApi
import com.davidm.satisbeer.featurehome.utils.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeApi: HomeApi,
    private val dispatchers: Dispatchers
) {

    /**
     * This will retrieve a paged list of all the available beers
     */
    suspend fun retrieveBeers(
        pageSize: Int,
        page: Int,
        beerName: String? = null
    ): List<Beer> {
        return withContext(dispatchers.io) {
            return@withContext homeApi.getBeers(beerName, page, pageSize)
        }
    }
}