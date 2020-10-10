package com.davidm.satisbeer.featurehome.view

import android.util.Log
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.repository.HomeRepository

class BeerListDataSource(
    private val coroutineScope: CoroutineScope,
    private val homeRepository: HomeRepository
) : PageKeyedDataSource<Int, Beer>() {

    var page = 1;

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Beer>
    ) {
        coroutineScope.launch {

            try {
                val result =
                    homeRepository.retrieveBeers(page)

                callback.onResult(
                    result,
                    page - 1,
                    page + 1
                )


            } catch (exception: Exception) {
                Log.e("loadInitialRedditData", exception.toString())
            }


        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Beer>
    ) {
        coroutineScope.launch {

            try {
                val result =
                    homeRepository.retrieveBeers(params.key)

                callback.onResult(
                    result,
                    page + 1
                )

            } catch (exception: Exception) {
                Log.e("loadAfterRedditData", exception.toString())
            }


        }//To change body of created functions use File | Settings | File Templates.
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Beer>
    ) {
        coroutineScope.launch {

            try {
                val result =
                    withContext(Dispatchers.IO) {
                        homeRepository.retrieveBeers(page)
                    }
                callback.onResult(
                    result,
                    page - 1
                )


            } catch (exception: Exception) {
                Log.e("loadBeforeRedditData", exception.toString())
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        coroutineScope.cancel()
    }

}