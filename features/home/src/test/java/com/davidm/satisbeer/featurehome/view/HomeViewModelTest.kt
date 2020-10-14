package com.davidm.satisbeer.featurehome.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.data.getDummyBeer
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
    fun searchBeerNullQuery() {
        val nullQueryResult = generateBeerList()
        coEvery { homeRepository.retrieveBeers(any(), any(), null) } returns nullQueryResult

        homeViewModel.searchForBeer(null)

        val result = homeViewModel.getBeerList().getOrAwaitValue()

        assert(result.minus(getDummyBeer()) == nullQueryResult)
        coVerify { homeRepository.retrieveBeers(any(), any(), null) }
    }

    @Test
    fun searchBeerEmptyQuery() {
        val nullQueryResult = generateBeerList()
        coEvery { homeRepository.retrieveBeers(any(), any(), null) } returns nullQueryResult

        // empty
        homeViewModel.searchForBeer("")

        coVerify { homeRepository.retrieveBeers(any(), any(), null) }
        val result = homeViewModel.getBeerList().getOrAwaitValue()

        assert(result.minus(getDummyBeer()) == nullQueryResult)

        // blank
        homeViewModel.searchForBeer(" ")
        coVerify { homeRepository.retrieveBeers(any(), any(), null) }

        val result2 = homeViewModel.getBeerList().getOrAwaitValue()
        assert(result2.minus(getDummyBeer()) == nullQueryResult)
    }

    @Test
    fun searchBeerRealQuery() {
        val queryResult = generateBeerList()
        val query = "blonde"
        coEvery { homeRepository.retrieveBeers(any(), any(), query) } returns queryResult

        homeViewModel.searchForBeer(query)

        val result = homeViewModel.getBeerList().getOrAwaitValue()

        assert(result.minus(getDummyBeer()) == queryResult)
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

        homeViewModel.searchForBeer(null)
        val viewModelNullResult = homeViewModel.getBeerList().getOrAwaitValue()
        assert(viewModelNullResult.minus(getDummyBeer()) == nullResult)

        // brown tests
        homeViewModel.searchForBeer("brown")
        val viewModelBrownResult = homeViewModel.getBeerList().getOrAwaitValue()
        assert(viewModelBrownResult.minus(getDummyBeer()) == brownResult)

        // blonde tests
        val viewModelBlondeResult = homeViewModel.getBeerList().getOrAwaitValue()

        homeViewModel.searchForBeer("blonde")
        assert(viewModelBlondeResult.minus(getDummyBeer()) == brownResult)

        homeViewModel.searchForBeer("BLONDE")
        assert(viewModelBlondeResult.minus(getDummyBeer()) == brownResult)

        // empty tests
        homeViewModel.searchForBeer("")
        val viewModelEmptyResult = homeViewModel.getBeerList().getOrAwaitValue()
        assert(viewModelEmptyResult.minus(getDummyBeer()) == nullResult)
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