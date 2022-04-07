package com.mojo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mojo.app.data.LayoutFetcher
import com.mojo.app.drawing.LayoutAdapter
import kotlinx.android.synthetic.main.activity_main.mojoView

class MainActivity : AppCompatActivity() {

    val layoutFetcher = LayoutFetcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutFetcher.fetch(resources, R.raw.layout)?.let { layout ->
            mojoView.layoutAdapter = LayoutAdapter(layout)
        }
    }
}
