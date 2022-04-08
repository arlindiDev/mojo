package com.mojo.app.engine.motion

import com.mojo.app.engine.Bounds
import com.mojo.app.engine.RenderObject
import com.mojo.app.engine.emptyBounds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.max
import kotlin.math.min

class MotionHandler(initialRenderedObjects: List<RenderObject>) {
    private val _renderObjects = MutableStateFlow(initialRenderedObjects)
    val renderedObjects = _renderObjects.asStateFlow()

    var initialX = 0f
    var initialY = 0f

    fun move(x: Float, y: Float) {
        val renderObject = findRenderedObject(x, y)

        val distanceX = initialX - x
        val distanceY = initialY - y
        initialX = x
        initialY = y

        val media = renderObject.media!!

        val movedRenderedObject = renderObject.copy(
            media = media.copy(
                crop = newBounds(renderObject, distanceX, distanceY)
            )
        )

        _renderObjects.tryEmit(replaceRenderObject(renderObject, movedRenderedObject))
    }

    private fun replaceRenderObject(old: RenderObject, new: RenderObject): List<RenderObject> {
        return renderedObjects.value.toMutableList()
            .map { if (it == old) new else it }.toList()
    }

    private fun newBounds(renderObject: RenderObject, x: Float, y: Float): Bounds {
        val bounds = renderObject.bounds
        val media = renderObject.media!!

        val width = bounds.right - bounds.left
        val height = bounds.bottom - bounds.top

        val heightDif = media.bitmap.height - height
        val widthDif = media.bitmap.width - width

        val crop = media.crop ?: emptyBounds()

        return with(crop) {
            Bounds(
                (left + x).ensureRange(0f, widthDif),
                (top + y).ensureRange(0f,heightDif),
                (right + x).ensureRange(width, media.bitmap.width.toFloat()),
                (bottom + y).ensureRange(height, media.bitmap.height.toFloat()),
            )
        }
    }

    private fun findRenderedObject(x: Float, y: Float): RenderObject {
        // last one on screen with media
        return renderedObjects.value.last()
    }
}

fun Float.ensureRange(min: Float, max: Float): Float {
    return min(max(this, min), max)
}
