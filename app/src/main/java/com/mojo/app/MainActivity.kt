package com.mojo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var moshi: Moshi = Moshi.Builder().build()
    var jsonAdapter: JsonAdapter<Input> = moshi.adapter(Input::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchInput()?.let {
            mojoView.input = it
        }
    }

    private fun fetchInput(): Input? {
        resources.openRawResource(R.raw.input).bufferedReader().use {
            return jsonAdapter.fromJson(it.readText())
        }
    }
}
