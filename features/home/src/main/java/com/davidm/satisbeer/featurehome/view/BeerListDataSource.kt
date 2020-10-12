package com.davidm.satisbeer.featurehome.view

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.repository.HomeRepository
import com.davidm.satisbeer.featurehome.utils.Dispatchers
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
class BeerListDataSource(
    coroutineScope: CoroutineScope,
    private val homeRepository: HomeRepository,
    dispatchers: Dispatchers,
    private val beerName: String?,
) : PageKeyedDataSource<Int, Beer>() {

    private val pageLoaderScope =
        CoroutineScope(coroutineScope.newCoroutineContext(dispatchers.main))

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Beer>
    ) {
        pageLoaderScope.launch {

            try {
                val result = homeRepository.retrieveBeers(params.requestedLoadSize, 1, beerName)

                callback.onResult(result, null, 2)

            } catch (exception: Exception) {
                Log.e("BeerListDataSource", "loadInitialRedditData", exception)
            }

        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Beer>
    ) {
        pageLoaderScope.launch {

            try {
                val result =
                    homeRepository.retrieveBeers(params.requestedLoadSize, params.key, beerName)
                callback.onResult(
                    result,
                    params.key + 1
                )

            } catch (exception: Exception) {
                Log.e("BeerListDataSource", "loadAfterRedditData", exception)
            }

        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Beer>
    ) {
        pageLoaderScope.launch {

            try {
                val result =
                    homeRepository.retrieveBeers(params.requestedLoadSize, params.key, beerName)

                callback.onResult(
                    result,
                    if (params.key == 1) null else params.key + 1
                )


            } catch (exception: Exception) {
                Log.e("BeerListDataSource", "loadBeforeRedditData", exception)
            }
        }
    }

    override fun invalidate() {
        pageLoaderScope.cancel()
        super.invalidate()
    }

}