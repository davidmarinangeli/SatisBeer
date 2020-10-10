package com.davidm.satisbeer.di

import android.app.Application
import com.davidm.satisbeer.SatisBeerApp
import com.davidm.satisbeer.featurehome.di.ViewModule
import com.davidm.satisbeer.featurehome.repository.HomeRepositoryModule
import com.davidm.satisbeer.network.di.BaseNetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class, HomeRepositoryModule::class, BaseNetworkModule::class,  ViewModelModule::class, ViewModule::class,
    ]
)
interface AppComponent : AndroidInjector<SatisBeerApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

}