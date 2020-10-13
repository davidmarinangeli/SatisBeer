package com.davidm.satisbeer

import HomeActivityTest
import com.davidm.satisbeer.di.AppComponent
import com.davidm.satisbeer.di.ViewModelModule
import com.davidm.satisbeer.featurehome.di.HomeRepositoryModule
import com.davidm.satisbeer.featurehome.di.ViewModule
import com.davidm.satisbeer.network.di.BaseNetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        HomeRepositoryModule::class, BaseNetworkModule::class,
        ViewModelModule::class, ViewModule::class, TestDispatchersModule::class
    ]
)
interface TestAppComponent : AppComponent {

    fun inject(homeActivityTest: HomeActivityTest)

}