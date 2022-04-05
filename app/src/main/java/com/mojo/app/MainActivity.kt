package com.mojo.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("ARLINDO", readRawJson())
    }

    private fun readRawJson(): String {
        resources.openRawResource(R.raw.input).bufferedReader().use {
            return it.readText()
        }
    }
}
