package com.mojo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mojo.app.drawing.LayoutAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var moshi: Moshi = Moshi.Builder().build()
    var jsonAdapter: JsonAdapter<Layout> = moshi.adapter(Layout::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchLayout()?.let {
            mojoView.layoutAdapter = LayoutAdapter(it)
        }
    }

    private fun fetchLayout(): Layout? {
        resources.openRawResource(R.raw.layout).bufferedReader().use {
            return jsonAdapter.fromJson(it.readText())
        }
    }
}
