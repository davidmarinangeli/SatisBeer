package com.davidm.satisbeer

import android.app.Application
import com.davidm.satisbeer.di.AppComponent
import com.davidm.satisbeer.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SatisBeerApp : Application(), HasAndroidInjector {

    lateinit var component: AppComponent
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.factory()
                .create(this)
        component.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

}
