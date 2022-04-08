package com.mojo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.mojo.app.data.LayoutFetcher
import com.mojo.app.di.Injector
import com.mojo.app.engine.LayoutAdapter
import com.mojo.app.engine.media.DefaultImageFetcher
import kotlinx.android.synthetic.main.activity_main.mojoView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn

class MainActivity : AppCompatActivity() {
    lateinit var layoutFetcher: LayoutFetcher
    lateinit var dispatcher: CoroutineDispatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val injector = (applicationContext as Injector)

        layoutFetcher = injector.layoutFetcherLocator().layoutFetcher(this)
        dispatcher = injector.dispatcherIO()
    }

    override fun onResume() {
        super.onResume()

        layoutFetcher.fetch()?.let { layout ->
            lifecycle.coroutineScope.launchWhenResumed {
                LayoutAdapter(layout, DefaultImageFetcher(::getIdFor))
                    .adapt(mojoView.layoutParams.width, mojoView.layoutParams.height)
                    .flowOn(dispatcher)
                    .collect { renderObjects ->
                        mojoView.renderObjects = renderObjects
                    }
            }
        }
    }

    private fun getIdFor(resourceName: String) =
        resources.getIdentifier(resourceName, "drawable", packageName)

}
