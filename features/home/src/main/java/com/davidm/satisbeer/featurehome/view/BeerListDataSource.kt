package com.davidm.satisbeer.featurehome.view

import androidx.paging.PagingSource
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.repository.HomeRepository
import com.davidm.satisbeer.featurehome.utils.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
class BeerListDataSource(
    private val homeRepository: HomeRepository,
    private val dispatchers: Dispatchers,
    private val beerName: String?,
) : PagingSource<Int, Beer>() {

    private val initialPageIndex: Int = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        val position = params.key ?: initialPageIndex

        return withContext(dispatchers.main) {
            try {
                val response = homeRepository.retrieveBeers(params.loadSize, position, beerName)
                LoadResult.Page(
                    data = response,
                    prevKey = if (position == initialPageIndex) null else position - 1,
                    nextKey = if (response.isEmpty()) null else position + 1
                )
            } catch (exception: IOException) {
                LoadResult.Error(exception)
            } catch (exception: HttpException) {
                LoadResult.Error(exception)
            }
        }
    }
}