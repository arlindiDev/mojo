package com.mojo.app.helpers.idlingresource
import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class DispatchersIdlingResourceRule(private vararg val dispatchers: DispatcherWithIdlingResource) : TestWatcher() {
    override fun starting(description: Description?) {
        dispatchers.forEach {
            IdlingRegistry.getInstance().register(it.idlingResource)
        }
    }

    override fun finished(description: Description?) {
        dispatchers.forEach {
            IdlingRegistry.getInstance().unregister(it.idlingResource)
        }
    }
}
