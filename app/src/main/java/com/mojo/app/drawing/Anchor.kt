package com.mojo.app.drawing

import android.graphics.RectF
import com.mojo.app.Input
import com.mojo.app.LeftAnchor
import com.mojo.app.RightAnchor
import kotlin.math.abs
import kotlin.math.min

interface Anchor {
    fun toParentBounds(input: Input): RectF
}

class Left(private val bounds: RectF) : Anchor {
    override fun toParentBounds(input: Input): RectF {
        val width = bounds.right - bounds.left

        val left = bounds.left + width * input.x.toFloat()
        val right = min(left + width * input.width.toFloat(), bounds.right)

        return bounds.copy(
            left = left,
            right = right
        )
    }
}

class Bottom(private val bounds: RectF) : Anchor {
    override fun toParentBounds(input: Input): RectF {
        val height = bounds.right - bounds.left

        val top = bounds.top + height * input.y.toFloat()
        val bottom =  min(top + height * input.height.toFloat(), bounds.bottom)

        return bounds.copy(
            top = top,
            bottom = bottom
        )
    }
}

class CenterX(private val bounds: RectF) : Anchor {
    override fun toParentBounds(input: Input): RectF {
        val right = (bounds.right * input.width.toFloat()) / 2
        val left = bounds.left - right

        val width = ((abs(left) + right) * input.x.toFloat()) * 2

        return bounds.copy(
            left = left + width,
            right = right + width,
        )
    }
}

class CenterY(private val bounds: RectF) : Anchor {
    override fun toParentBounds(input: Input): RectF {
        val bottom = (bounds.bottom * input.height.toFloat()) / 2
        val top = bounds.top - bottom

        val height = ((abs(top) + bottom) * input.y.toFloat()) * 2

        return bounds.copy(
            top = top + height,
            bottom = bottom + height
        )
    }
}

fun RectF.toAnchor(input: Input): RectF {
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
