package com.mojo.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class MainActivity : AppCompatActivity() {

    var moshi: Moshi = Moshi.Builder().build()
    var jsonAdapter: JsonAdapter<Input> = moshi.adapter(Input::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("ARLINDO", readRawJson().toString())
    }

    private fun readRawJson(): Input? {
        resources.openRawResource(R.raw.input).bufferedReader().use {
            return jsonAdapter.fromJson(it.readText())
        }
    }
}
