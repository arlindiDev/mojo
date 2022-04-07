package com.mojo.app.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface AppDispatchers {
    val IO: CoroutineDispatcher
}

class DefaultDispatchers : AppDispatchers {
    override val IO: CoroutineDispatcher = Dispatchers.IO
}
