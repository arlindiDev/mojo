package com.mojo.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("ARLINDO", readRawJson().toString())
    }

    private fun readRawJson(): Input {
        resources.openRawResource(R.raw.input).bufferedReader().use {
            return gson.fromJson(it.readText(), object: TypeToken<Input>() {}.type)
        }
    }
}
