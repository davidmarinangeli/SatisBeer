package com.davidm.satisbeer.testdispatchers

import com.davidm.satisbeer.featurehome.utils.Dispatchers
import dagger.Reusable
import javax.inject.Inject

@Reusable
class TestDispatchers @Inject constructor() : Dispatchers {

    override val io: IdlingDispatcher = IdlingDispatcher(kotlinx.coroutines.Dispatchers.IO)
    override val main: IdlingDispatcher = IdlingDispatcher(kotlinx.coroutines.Dispatchers.Main)
    override val cpu: IdlingDispatcher = IdlingDispatcher(kotlinx.coroutines.Dispatchers.Default)

}