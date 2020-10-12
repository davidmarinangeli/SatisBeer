package com.davidm.satisbeer.featurehome.repository

import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.repository.network.HomeApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.IOException

class HomeRepositoryTest {

    @MockK
    lateinit var homeApi: HomeApi

    lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        homeRepository = HomeRepository(homeApi)
    }

    @Test
    fun retrieveBeers() {
        val beers = listOf(
            Beer(
                0, 0.0, "", "", "", "", emptyList(), "", "", ""
            )
        )

        coEvery { homeApi.getBeers(any(), any(), any()) } returns beers

        val returnedBeers = runBlocking { homeRepository.retrieveBeers(25, 1) }

        assert(beers == returnedBeers)
    }

    @Test
    fun retrieveBeersFails() {
        coEvery { homeApi.getBeers(any(), any(), any()) } throws IOException("Network error")

        kotlin.runCatching {
            runBlocking { homeRepository.retrieveBeers(25, 1) }
        }.onSuccess {
            fail("This should have failed")
        }
    }
}