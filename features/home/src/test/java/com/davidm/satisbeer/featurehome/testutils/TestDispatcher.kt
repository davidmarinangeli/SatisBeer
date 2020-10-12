package com.davidm.satisbeer.featurehome.testutils

import com.davidm.satisbeer.featurehome.utils.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcher : Dispatchers {

  val dispatcher = TestCoroutineDispatcher()

  override val io: CoroutineDispatcher = dispatcher
  override val main: CoroutineDispatcher = dispatcher
  override val cpu: CoroutineDispatcher = dispatcher
}