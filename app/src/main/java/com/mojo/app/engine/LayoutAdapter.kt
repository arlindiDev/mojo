package com.mojo.app.engine

import com.mojo.app.data.Layout
import com.mojo.app.engine.media.ImageFetcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LayoutAdapter(
    private val layout: Layout,
    private val imageFetcher: ImageFetcher
) {
    private lateinit var renderObjects: MutableList<RenderObject>

    fun adapt(width: Int, height: Int): Flow<List<RenderObject>> {
        return flow {
            if (width > 0 && height > 0) {
                renderObjects = mutableListOf()

                val startingBounds = Bounds(0.0f, 0.0f, width.toFloat(), height.toFloat())

                flattenLayout(startingBounds, layout)

                emit(renderObjects.toList())
            }
        }
    }

    private fun flattenLayout(bounds: Bounds, layout: Layout) {
        val parentBounds = bounds.toAnchor(layout)

        with(layout) {
            val mediaObject = media?.let { media ->
                imageFetcher.bitmapFromResource(media)
                adaptMedia(parentBounds, layout.mediaContentMode!!, imageFetcher)
            }

            renderObjects.add(RenderObject(parentBounds, backgroundColor, mediaObject))

            children.forEach { childLayout ->
                val childBounds = parentBounds.withPadding(layout.padding)

                flattenLayout(childBounds, childLayout)
            }
        }
    }
}
