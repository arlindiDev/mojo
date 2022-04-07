package com.mojo.app.data

import android.content.res.Resources
import androidx.annotation.RawRes
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class LayoutFetcher(val layout: String) {
    val moshi: Moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<Layout> = moshi.adapter(Layout::class.java)

    fun fetch(): Layout? {
        return jsonAdapter.fromJson(layout)
    }
}

fun fromResource(resources: Resources, @RawRes id: Int): String {
    return resources.openRawResource(id).bufferedReader().use { it.readText() }
}
