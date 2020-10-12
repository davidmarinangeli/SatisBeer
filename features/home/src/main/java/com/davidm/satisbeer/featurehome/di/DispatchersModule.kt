package com.davidm.satisbeer.featurehome.di

import com.davidm.satisbeer.featurehome.utils.Dispatchers
import com.davidm.satisbeer.featurehome.utils.ProdDispatchers
import dagger.Module
import dagger.Provides

@Module
object DispatchersModule {

    @Provides
    fun providesDispatchers(prodDispatchers: ProdDispatchers): Dispatchers = prodDispatchers

}