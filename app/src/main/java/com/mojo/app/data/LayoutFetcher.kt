package com.mojo.app.data

import android.content.res.Resources
import androidx.annotation.RawRes
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class LayoutFetcher {
    val moshi: Moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<Layout> = moshi.adapter(Layout::class.java)

    fun fetch(resources: Resources, @RawRes id: Int): Layout? {
        resources.openRawResource(id).bufferedReader().use {
            return jsonAdapter.fromJson(it.readText())
        }
    }
}
