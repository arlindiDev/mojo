package com.mojo.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.mojo.app.data.LayoutFetcher
import com.mojo.app.di.DefaultLayoutFetcherLocator
import com.mojo.app.di.LayoutFetcherLocator

class TestMojoApplication : Application(), Injector {
    lateinit var layoutFetcherLocator: LayoutFetcherLocator

    var layout: String = ""

    override fun onCreate() {
        super.onCreate()

        layoutFetcherLocator = DefaultLayoutFetcherLocator()
    }

    override fun layoutFetcherLocator() = object : LayoutFetcherLocator {
        override fun layoutFetcher(context: Context): LayoutFetcher {
            return LayoutFetcher(layout)
        }
    }
}
