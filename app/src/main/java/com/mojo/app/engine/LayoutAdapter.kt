package com.mojo.app.engine

import com.mojo.app.data.Layout
import com.mojo.app.data.defaultLayout

class LayoutAdapter(private val layout: Layout) {
    private lateinit var renderObjects: MutableList<RenderObjet>

    fun adapt(width: Float, height: Float): List<RenderObjet> {
        renderObjects = mutableListOf()

        val startingBounds = Bounds(0.0f, 0.0f, width, height)

        flattenLayout(startingBounds, layout)

        return renderObjects.toList()
    }

    private fun flattenLayout(bounds: Bounds, layout: Layout) {
        val parentBounds = bounds.toAnchor(layout)

        with(layout) {
            renderObjects.add(RenderObjet(parentBounds, backgroundColor))

            children.forEach { childLayout ->
                val childBounds = parentBounds.withPadding(layout.padding)

                flattenLayout(childBounds, childLayout)
            }
        }
    }
}

val defaultLayoutAdapter = LayoutAdapter(defaultLayout)
