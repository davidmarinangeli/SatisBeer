package com.davidm.satisbeer

class TestSatisBeerApp : SatisBeerApp() {
    override fun getComponent(): TestAppComponent {
        return DaggerTestAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: TestSatisBeerApp
    }
}