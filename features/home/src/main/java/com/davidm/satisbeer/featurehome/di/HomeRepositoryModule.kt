package com.davidm.satisbeer.featurehome.di

import com.davidm.satisbeer.featurehome.repository.network.HomeApi
import com.davidm.satisbeer.network.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class HomeRepositoryModule {

    @Provides
    fun provideHomeApi(retrofit: Retrofit.Builder): HomeApi {
        return retrofit
            .baseUrl(BASE_URL)
            .build()
            .create(HomeApi::class.java)
    }
}