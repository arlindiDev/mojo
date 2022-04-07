package com.mojo.app.engine

import android.graphics.Bitmap
import com.mojo.app.data.Layout

class LayoutAdapter(
    private val layout: Layout,
    private val bitmapFromResource: ((resourceName: String) -> Bitmap)
) {
    private lateinit var renderObjects: MutableList<RenderObject>

    fun adapt(width: Float, height: Float): List<RenderObject> {
        renderObjects = mutableListOf()

        val startingBounds = Bounds(0.0f, 0.0f, width, height)

        flattenLayout(startingBounds, layout)

        return renderObjects.toList()
    }

    private fun flattenLayout(bounds: Bounds, layout: Layout) {
        val parentBounds = bounds.toAnchor(layout)

        with(layout) {
            val mediaRender = media?.let { media(bounds, layout) }

            renderObjects.add(RenderObject(parentBounds, backgroundColor, mediaRender))

            children.forEach { childLayout ->
                val childBounds = parentBounds.withPadding(layout.padding)

                flattenLayout(childBounds, childLayout)
            }
        }
    }

    private fun media(bounds: Bounds, layout: Layout): Media {
        val bitmap = bitmapFromResource.invoke(layout.media!!)
        return Media(bitmap, bounds)
    }
}
