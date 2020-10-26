package com.davidm.satisbeer.featurehome.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.repository.HomeRepository
import com.davidm.satisbeer.featurehome.testutils.CoroutineTestRule
import com.davidm.satisbeer.featurehome.testutils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random


@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @Rule
    @JvmField
    val coroutineTestRule = CoroutineTestRule()

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @MockK
    lateinit var homeRepository: HomeRepository

    lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        homeViewModel = HomeViewModel(homeRepository, coroutineTestRule.dispatcherProvider)

        coEvery { homeRepository.retrieveBeers(any(), any(), any()) } returns emptyList()
    }

    @Test
    fun searchBeerEmptyQuery() {
        val nullQueryResult = generateBeerList()
        coEvery { homeRepository.retrieveBeers(any(), any(), null) } returns nullQueryResult

        // empty
        homeViewModel.searchForBeer("")

        coVerify { homeRepository.retrieveBeers(any(), any(), null) }

        assert(homeViewModel.mutableLiveData.getOrAwaitValue() == nullQueryResult)

        // blank
        homeViewModel.searchForBeer(" ")
        coVerify { homeRepository.retrieveBeers(any(), any(), null) }

        assert(homeViewModel.mutableLiveData.getOrAwaitValue() == nullQueryResult)
    }

    @Test
    fun searchBeerRealQuery() {
        // init test
        val queryResult = generateBeerList()
        val query = "blonde"
        coEvery { homeRepository.retrieveBeers(any(), any(), query) } returns queryResult

        // exec
        homeViewModel.searchForBeer(query)

        // assert the result
        assert(homeViewModel.mutableLiveData.getOrAwaitValue() == queryResult)
        coVerify { homeRepository.retrieveBeers(any(), any(), query) }
    }

    @Test
    fun searchBeerManyQueries() {
        val blondeResult = generateBeerList()
        val brownResult = generateBeerList()
        val nullResult = generateBeerList()

        coEvery { homeRepository.retrieveBeers(any(), any(), "blonde") } returns blondeResult
        coEvery { homeRepository.retrieveBeers(any(), any(), "brown") } returns brownResult
        coEvery { homeRepository.retrieveBeers(any(), any(), null) } returns nullResult

        homeViewModel.searchForBeer(" ")
        assert(homeViewModel.mutableLiveData.getOrAwaitValue() == nullResult)

        // brown tests
        homeViewModel.searchForBeer("brown")
        assert(homeViewModel.mutableLiveData.getOrAwaitValue() == brownResult)

        // blonde tests
        homeViewModel.searchForBeer("blonde")
        assert(homeViewModel.mutableLiveData.getOrAwaitValue() == brownResult)

        homeViewModel.searchForBeer("BLONDE")
        assert(homeViewModel.mutableLiveData.getOrAwaitValue() == brownResult)

        // empty tests
        homeViewModel.searchForBeer("")
        assert(homeViewModel.mutableLiveData.getOrAwaitValue() == nullResult)
    }

    /**
     * Generates a random list of random beers. Randomly
     */
    private fun generateBeerList(): List<Beer> {
        val random = Random(10)
        val beers = mutableListOf<Beer>()

        for (i in (0..random.nextInt(20) + 1)) {
            beers.add(
                Beer(
                    random.nextLong(), 0.0, "", "", "", "", emptyList(), "", "", ""
                )
            )
        }

        return beers.toList()
    }
}