package com.mojo.app.helpers.idlingresource

import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

fun delegateDispatchWithCounting(
    delegateDispatcher: CoroutineDispatcher,
    context: CoroutineContext,
    block: Runnable,
    idlingResource: CountingIdlingResource
) {
    idlingResource.increment()
    delegateDispatcher.dispatch(context) {
        try {
            block.run()
        } finally {
            idlingResource.decrement()
        }
    }
}

class EspressoTrackedDispatcher(private val delegateDispatcher: CoroutineDispatcher) : CoroutineDispatcher(), DispatcherWithIdlingResource {
    override val idlingResource: CountingIdlingResource = CountingIdlingResource("EspressoTrackedDispatcher for $delegateDispatcher")

    override fun dispatch(context: CoroutineContext, block: Runnable) = delegateDispatchWithCounting(delegateDispatcher, context, block, idlingResource)
}
