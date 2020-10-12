package com.davidm.satisbeer.featurehome.utils

import dagger.Reusable
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@Reusable
class ProdDispatchers @Inject constructor() : Dispatchers {

    override val io: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.IO
    override val main: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Main
    override val cpu: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Default

}