package com.davidm.satisbeer.di

import com.davidm.satisbeer.SatisBeerApp
import com.davidm.satisbeer.featurehome.di.DispatchersModule
import com.davidm.satisbeer.featurehome.di.HomeRepositoryModule
import com.davidm.satisbeer.featurehome.di.ViewModule
import com.davidm.satisbeer.network.di.BaseNetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HomeRepositoryModule::class, BaseNetworkModule::class,
        ViewModelModule::class, ViewModule::class, DispatchersModule::class
    ]
)
interface AppComponent {
    fun inject(satisBeerApp: SatisBeerApp)

}