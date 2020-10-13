package com.davidm.satisbeer

import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector

class TestSatisBeerApp : SatisBeerApp(), HasAndroidInjector {

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun getComponent(): TestAppComponent {
        return DaggerTestAppComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        (appComponent as TestAppComponent).inject(this)
    }

    companion object {
        lateinit var instance: TestSatisBeerApp
    }
}