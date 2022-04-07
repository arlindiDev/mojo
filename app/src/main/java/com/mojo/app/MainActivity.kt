package com.mojo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mojo.app.engine.LayoutAdapter
import kotlinx.android.synthetic.main.activity_main.mojoView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val injector = (applicationContext as Injector)

        val layoutFetcher = injector.layoutFetcherLocator().layoutFetcher(this)

        layoutFetcher.fetch()?.let { layout ->
            mojoView.layoutAdapter = LayoutAdapter(layout)
        }
    }
}
