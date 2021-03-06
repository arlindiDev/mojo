package com.mojo.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.mojo.app.data.LayoutFetcher
import com.mojo.app.di.Injector
import com.mojo.app.engine.LayoutAdapter
import com.mojo.app.engine.media.DefaultImageFetcher
import com.mojo.app.engine.motion.Coordinates
import com.mojo.app.engine.motion.MotionHandler
import kotlinx.android.synthetic.main.activity_main.mojoView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    lateinit var layoutFetcher: LayoutFetcher
    lateinit var dispatcher: CoroutineDispatcher
    lateinit var motionHandler: MotionHandler

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val injector = (applicationContext as Injector)

        layoutFetcher = injector.layoutFetcherLocator().layoutFetcher(this)
        dispatcher = injector.dispatcherIO()

        mojoView.setOnTouchListener { _, event ->
            when (event.action) {
                ACTION_DOWN -> {
                    motionHandler.initialRaw = Coordinates(event.rawX, event.rawY)
                    motionHandler.initialRelativeToView = Coordinates(event.x, event.y)
                }
                ACTION_MOVE ->
                    motionHandler.move(
                        Coordinates(event.x, event.y)
                    )
                ACTION_UP -> {
                    motionHandler.initialRaw = Coordinates()
                    motionHandler.initialRelativeToView = Coordinates()
                }
            }

            return@setOnTouchListener true
        }
    }

    override fun onResume() {
        super.onResume()

        layoutFetcher.fetch()?.let { layout ->
            lifecycle.coroutineScope.launchWhenResumed {
                LayoutAdapter(layout, DefaultImageFetcher(::getIdFor))
                    .adapt(mojoView.layoutParams.width, mojoView.layoutParams.height)
                    .onEach { renderObjects ->
                        motionHandler = MotionHandler(renderObjects)
                    }
                    .flatMapLatest {
                        motionHandler.renderedObjects
                    }
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
