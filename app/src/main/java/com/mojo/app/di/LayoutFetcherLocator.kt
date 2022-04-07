package com.mojo.app.di

import android.content.Context
import com.mojo.app.R
import com.mojo.app.data.LayoutFetcher
import com.mojo.app.data.fromResource

interface LayoutFetcherLocator {
    fun layoutFetcher(context: Context): LayoutFetcher
}

class DefaultLayoutFetcherLocator : LayoutFetcherLocator {
    override fun layoutFetcher(context: Context): LayoutFetcher {
        return LayoutFetcher(fromResource(context.resources, R.raw.layout))
    }
}
