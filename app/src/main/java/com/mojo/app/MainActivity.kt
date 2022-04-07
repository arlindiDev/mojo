package com.mojo.app

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.mojo.app.engine.LayoutAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val injector = (applicationContext as Injector)

        val layoutFetcher = injector.layoutFetcherLocator().layoutFetcher(this)

        layoutFetcher.fetch()?.let { layout ->
            mojoView.layoutAdapter = LayoutAdapter(layout, ::bitmapFromResource)
        }
    }

    private fun bitmapFromResource(resourceName: String) : Bitmap {
        val id = resources.getIdentifier(resourceName, "drawable", packageName)

        return AppCompatResources.getDrawable(this, id)!!.toBitmap()
    }
}
