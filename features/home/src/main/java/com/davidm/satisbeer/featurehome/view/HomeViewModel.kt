package com.davidm.satisbeer.featurehome.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.repository.HomeRepository
import com.davidm.satisbeer.featurehome.utils.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.util.*
import javax.inject.Inject

private const val PAGE_SIZE = 25

@ExperimentalCoroutinesApi
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val dispatchers: Dispatchers
) : ViewModel() {

    val mutableLiveData = MutableLiveData<PagingData<Beer>>()
    private var liveData: LiveData<PagingData<Beer>>? = null
    private val observer = { item: PagingData<Beer> -> mutableLiveData.value = item }

    private val scopeViewModel = CoroutineScope(SupervisorJob() + dispatchers.main)

    init {
        initBeerList()
    }

    fun initBeerList() {
        searchForBeer("")
    }

    fun searchForBeer(beerName: String) {
        val formattedBeerName = beerName
            .ifBlank { null }
            ?.replace(" ", "_")
            ?.toLowerCase(Locale.getDefault())

        val dataSourceFactory = createDataSourceForBeers(formattedBeerName)
        liveData?.removeObserver(observer)
        liveData = dataSourceFactory.flow.asLiveData().cachedIn(scopeViewModel)

        liveData?.observeForever(observer)
    }

    private fun createDataSourceForBeers(beerName: String? = null): Pager<Int, Beer> {
        return Pager(PagingConfig(pageSize = PAGE_SIZE), pagingSourceFactory = {
            BeerListDataSource(
                homeRepository, dispatchers, beerName
            )
        })
    }

    override fun onCleared() {
        scopeViewModel.cancel()
        liveData?.removeObserver(observer)
        super.onCleared()
    }

}