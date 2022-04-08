package com.mojo.app.di

import android.app.Application
import com.mojo.app.di.DefaultDispatchers
import com.mojo.app.di.DefaultLayoutFetcherLocator
import com.mojo.app.di.LayoutFetcherLocator
import kotlinx.coroutines.CoroutineDispatcher

interface Injector {
    fun layoutFetcherLocator(): LayoutFetcherLocator

    fun dispatcherIO(): CoroutineDispatcher
}

class MojoApplication : Application(), Injector {
    lateinit var layoutFetcherLocator: LayoutFetcherLocator

    override fun onCreate() {
        super.onCreate()

        layoutFetcherLocator = DefaultLayoutFetcherLocator()
    }

    override fun layoutFetcherLocator() = layoutFetcherLocator

    override fun dispatcherIO() = DefaultDispatchers().IO
}
