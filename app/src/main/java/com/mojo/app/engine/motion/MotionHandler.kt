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

    var initialRaw = Coordinates()
    var initialRelativeToView = Coordinates()

    fun move(raw: Coordinates) {
        val renderObject = findRenderedObject() ?: return

        val distanceX = initialRaw.x - raw.x
        val distanceY = initialRaw.y - raw.y

        initialRaw = Coordinates(raw.x, raw.y)

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

    private fun findRenderedObject(): RenderObject? {
        return renderedObjects.value
            .lastOrNull { it.media?.crop != null && it.bounds.inRange(initialRelativeToView.x, initialRelativeToView.y)  }
    }
}

fun Float.ensureRange(min: Float, max: Float): Float {
    return min(max(this, min), max)
}

fun Bounds.inRange(x: Float, y: Float): Boolean {
   return x in left..right && y in top..bottom
}
