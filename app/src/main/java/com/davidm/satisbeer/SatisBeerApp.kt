package com.davidm.satisbeer

import android.app.Application
import com.davidm.satisbeer.di.AppComponent
import com.davidm.satisbeer.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class SatisBeerApp : Application(), HasAndroidInjector {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = getComponent()
        appComponent.inject(this)
    }

    open fun getComponent(): AppComponent {
        return DaggerAppComponent.factory()
            .create(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    companion object {
        lateinit var instance: SatisBeerApp
    }

}
