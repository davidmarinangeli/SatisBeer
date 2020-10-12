package com.davidm.satisbeer.featurehome.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
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

    private val beerListLiveData: MutableLiveData<PagedList<Beer>> = MutableLiveData()
    private var livePagedListInternal: LiveData<PagedList<Beer>>? = null

    private val dataSourceObserver = Observer<PagedList<Beer>> { beerListLiveData.postValue(it) }
    private val scopeViewModel = CoroutineScope(SupervisorJob() + dispatchers.main)

    init {
        searchForBeer()
    }

    fun searchForBeer(beerName: String? = null) {
        livePagedListInternal?.removeObserver(dataSourceObserver)

        val formattedBeerName = beerName
            ?.ifBlank { null }
            ?.replace(" ", "_")
            ?.toLowerCase(Locale.getDefault())

        val dataSourceFactory = createDataSourceForBeers(formattedBeerName)

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        livePagedListInternal = LivePagedListBuilder(dataSourceFactory, config)
            .setInitialLoadKey(1)
            .build().also {
                it.observeForever(dataSourceObserver)
            }
    }

    fun getBeerList(): LiveData<PagedList<Beer>> = beerListLiveData

    override fun onCleared() {
        scopeViewModel.cancel()
        livePagedListInternal?.removeObserver(dataSourceObserver)
        super.onCleared()
    }

    private fun createDataSourceForBeers(beerName: String? = null): DataSource.Factory<Int, Beer> {
        return object : DataSource.Factory<Int, Beer>() {
            private var previousDataSource: BeerListDataSource? = null

            override fun create(): DataSource<Int, Beer> {
                previousDataSource?.invalidate()

                return BeerListDataSource(scopeViewModel, homeRepository, dispatchers, beerName)
                    .also { previousDataSource = it }
            }
        }
    }

}