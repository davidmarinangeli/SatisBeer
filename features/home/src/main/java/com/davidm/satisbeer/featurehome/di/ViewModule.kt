package com.davidm.satisbeer.featurehome.di

import com.davidm.satisbeer.featurehome.view.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeActivityInjector(): HomeActivity

}