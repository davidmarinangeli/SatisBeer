package com.davidm.satisbeer

import com.davidm.satisbeer.featurehome.utils.Dispatchers
import com.davidm.satisbeer.testdispatchers.TestDispatchers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDispatchersModule {

    @Provides
    @Singleton
    fun providesTestDispatchers(testDispatchers: TestDispatchers): Dispatchers = testDispatchers

}