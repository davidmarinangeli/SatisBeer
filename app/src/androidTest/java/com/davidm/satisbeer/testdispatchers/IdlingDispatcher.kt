package com.davidm.satisbeer.testdispatchers

/*
 * Copyright (C) 2020 Rick Busarow
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext

class IdlingDispatcher(
    private val delegate: CoroutineDispatcher
) : CoroutineDispatcher() {

    val counter: CountingIdlingResource = CountingIdlingResource("IdlingResource for $this")

    fun isIdle(): Boolean = counter.isIdleNow

    override fun dispatch(context: CoroutineContext, block: Runnable) {

        val runnable = Runnable {
            counter.increment()
            try {
                block.run()
            } finally {
                counter.decrement()
            }
        }
        delegate.dispatch(context, runnable)
    }

    override fun toString(): String = "CountingDispatcher delegating to $delegate"
}
