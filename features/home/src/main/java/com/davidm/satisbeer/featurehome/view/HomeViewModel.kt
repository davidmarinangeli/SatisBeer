package com.davidm.satisbeer.featurehome.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private var beerListLiveData: LiveData<PagedList<Beer>>

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()

        beerListLiveData = initializedPagedListBuilder(config).build()
    }

    fun getBeerList(): LiveData<PagedList<Beer>> = beerListLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Beer> {

        val dataSourceFactory = object : DataSource.Factory<Int, Beer>() {
            override fun create(): DataSource<Int, Beer> {
                return BeerListDataSource(
                    viewModelScope,
                    homeRepository,
                )
            }
        }

        return LivePagedListBuilder(dataSourceFactory, config)
    }
}