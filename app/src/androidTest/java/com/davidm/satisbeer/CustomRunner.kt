package com.davidm.satisbeer

import android.app.Application
import android.content.Context
import androidx.test.espresso.IdlingRegistry
import androidx.test.runner.AndroidJUnitRunner
import com.jakewharton.espresso.OkHttp3IdlingResource


class CustomRunner : AndroidJUnitRunner() {

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(
            cl,
            TestSatisBeerApp::class.java.canonicalName,
            context
        ) as TestSatisBeerApp
    }

    override fun callApplicationOnCreate(app: Application?) {
        super.callApplicationOnCreate(app)

        setupIdlerResources()
    }

    private fun setupIdlerResources() {
        val testAppComponent = TestSatisBeerApp.instance.appComponent as TestAppComponent
        val okHttpClient = testAppComponent.provideOkHttpClient()
        val dispatchers = testAppComponent.provideTestDispatchers()

        // okhttp idler
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("OkHttp", okHttpClient))

        // dispatcher idler
        IdlingRegistry.getInstance().register(dispatchers.cpu.counter)
        IdlingRegistry.getInstance().register(dispatchers.main.counter)
        IdlingRegistry.getInstance().register(dispatchers.io.counter)
    }
}