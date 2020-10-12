package com.davidm.satisbeer.featurehome.utils

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatchers {

    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val cpu: CoroutineDispatcher

}