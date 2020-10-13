package com.davidm.satisbeer

import org.junit.rules.TestWatcher
import org.junit.runner.Description

class IdlingDispatcherRule : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)

    }

    override fun finished(description: Description?) {
        super.finished(description)
    }
}