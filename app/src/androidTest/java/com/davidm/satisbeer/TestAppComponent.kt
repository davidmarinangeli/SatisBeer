package com.davidm.satisbeer

import com.davidm.satisbeer.di.AppComponent
import com.davidm.satisbeer.di.ViewModelModule
import com.davidm.satisbeer.featurehome.di.HomeRepositoryModule
import com.davidm.satisbeer.featurehome.di.ViewModule
import com.davidm.satisbeer.network.di.BaseNetworkModule
import com.davidm.satisbeer.testdispatchers.TestDispatchers
import dagger.Component
import dagger.android.AndroidInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class, HomeRepositoryModule::class, BaseNetworkModule::class,
        ViewModelModule::class, ViewModule::class, TestDispatchersModule::class
    ]
)
interface TestAppComponent : AppComponent {
    fun inject(testSatisBeerApp: TestSatisBeerApp)

    fun provideTestDispatchers(): TestDispatchers
    fun provideOkHttpClient(): OkHttpClient

}