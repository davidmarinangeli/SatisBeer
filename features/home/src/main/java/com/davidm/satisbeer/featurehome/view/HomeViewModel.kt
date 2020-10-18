package com.davidm.satisbeer.featurehome.view

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
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

    var flow = Pager(PagingConfig(pageSize = PAGE_SIZE), pagingSourceFactory = {
        BeerListDataSource(
            scopeViewModel, homeRepository, dispatchers, null
        )
    }).flow
    private val scopeViewModel = CoroutineScope(SupervisorJob() + dispatchers.main)

    fun searchForBeer(beerName: String? = null) {
        val formattedBeerName = beerName
            ?.ifBlank { null }
            ?.replace(" ", "_")
            ?.toLowerCase(Locale.getDefault())
    }

    override fun onCleared() {
        scopeViewModel.cancel()
        super.onCleared()
    }

}