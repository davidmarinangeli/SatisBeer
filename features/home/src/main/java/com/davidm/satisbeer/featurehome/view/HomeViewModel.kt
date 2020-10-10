package com.davidm.satisbeer.featurehome.view

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.repository.HomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

private const val PAGE_SIZE = 25

@ExperimentalCoroutinesApi
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val beerListLiveData: MutableLiveData<PagedList<Beer>> = MutableLiveData()
    private var livePagedListInternal: LiveData<PagedList<Beer>>? = null

    private val dataSourceObserver = Observer<PagedList<Beer>> { beerListLiveData.postValue(it) }

    init {
        searchForBeer()
    }

    fun searchForBeer(beerName: String? = null) {
        livePagedListInternal?.removeObserver(dataSourceObserver)

        val formattedBeerName = beerName
            ?.ifEmpty { null }
            ?.replace(" ", "_")

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
        livePagedListInternal?.removeObserver(dataSourceObserver)
        super.onCleared()
    }

    private fun createDataSourceForBeers(beerName: String? = null): DataSource.Factory<Int, Beer> {
        return object : DataSource.Factory<Int, Beer>() {
            private var previousDataSource: BeerListDataSource? = null

            override fun create(): DataSource<Int, Beer> {
                previousDataSource?.invalidate()

                return BeerListDataSource(viewModelScope, homeRepository, beerName)
                    .also { previousDataSource = it }
            }
        }
    }

}