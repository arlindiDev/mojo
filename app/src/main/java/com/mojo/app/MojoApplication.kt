package com.mojo.app

import android.app.Application
import com.mojo.app.di.DefaultLayoutFetcherLocator
import com.mojo.app.di.LayoutFetcherLocator

interface Injector {
    fun layoutFetcherLocator(): LayoutFetcherLocator
}

class MojoApplication : Application(), Injector {
    lateinit var layoutFetcherLocator: LayoutFetcherLocator

    override fun onCreate() {
        super.onCreate()

        layoutFetcherLocator = DefaultLayoutFetcherLocator()
    }

    override fun layoutFetcherLocator() = layoutFetcherLocator
}
