package com.davidm.satisbeer.featurehome.testutils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule : TestWatcher() {

  val dispatcherProvider = TestDispatcher()
  val dispatcher = dispatcherProvider.dispatcher

  override fun starting(description: Description?) {
    super.starting(description)
    kotlinx.coroutines.Dispatchers.setMain(dispatcher)
  }

  override fun finished(description: Description?) {
    super.finished(description)
    kotlinx.coroutines.Dispatchers.resetMain()
    dispatcher.cleanupTestCoroutines()
  }

  fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit){
    dispatcher.runBlockingTest(block)
  }
}