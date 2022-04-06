package com.mojo.app.drawing

import com.mojo.app.Input
import com.mojo.app.LeftAnchor
import com.mojo.app.RightAnchor
import kotlin.math.min

interface Anchor {
    fun toParentBounds(input: Input): Rect
}

class Left(private val bounds: Rect) : Anchor {
    override fun toParentBounds(input: Input): Rect {
        val width = bounds.right - bounds.left

        val left = bounds.left + width * input.x.toFloat()
        val right = min(left + width * input.width.toFloat(), bounds.right)

        return bounds.copy(
            left = left,
            right = right
        )
    }
}

class Bottom(private val bounds: Rect) : Anchor {
    override fun toParentBounds(input: Input): Rect {
        val height = bounds.right - bounds.left

        val top = bounds.top + height * input.y.toFloat()
        val bottom = min(top + height * input.height.toFloat(), bounds.bottom)

        return bounds.copy(
            top = top,
            bottom = bottom
        )
    }
}

class CenterX(private val bounds: Rect) : Anchor {
    override fun toParentBounds(input: Input): Rect {
        val width = bounds.right - bounds.left

        val left = bounds.left + width * input.x.toFloat()
        val right = left + width * input.width.toFloat()

        val center = (right - left) / 2
        return bounds.copy(
            left = left - center,
            right = right - center,
        )
    }
}

class CenterY(private val bounds: Rect) : Anchor {
    override fun toParentBounds(input: Input): Rect {
        val height = bounds.right - bounds.left

        val top = bounds.top + height * input.y.toFloat()
        val bottom = top + height * input.height.toFloat()

        val center = (bottom - top) / 2
        return bounds.copy(
            top = top - center,
            bottom = bottom - center
        )
    }
}

fun Rect.toAnchor(input: Input): Rect {
    val anchorX = when (input.anchorX) {
        LeftAnchor.left -> Left(this)
        LeftAnchor.center -> CenterX(this)
        else -> Left(this)
    }

    val anchorY = when (input.anchorY) {
        RightAnchor.bottom -> Bottom(this)
        RightAnchor.center -> CenterY(this)
        else -> Bottom(this)
    }

    val anchorXBounds = anchorX.toParentBounds(input)
    val anchorYBounds = anchorY.toParentBounds(input)

    return anchorXBounds.copy(top = anchorYBounds.top, bottom = anchorYBounds.bottom)
}
