package com.mojo.app.drawing

import android.graphics.RectF
import com.mojo.app.Input
import com.mojo.app.LeftAnchor
import com.mojo.app.RightAnchor
import kotlin.math.abs

interface Anchor {
    fun toParentBounds(input: Input): RectF
}

class Left(private val bounds: RectF) : Anchor {
    override fun toParentBounds(input: Input): RectF {
        val left = bounds.left + bounds.right * input.x.toFloat()
        val right = left * input.x.toFloat() + bounds.right * input.width.toFloat()

        return bounds.copy(
            left = left,
            right = right
        )
    }
}

class Bottom(private val bounds: RectF) : Anchor {
    override fun toParentBounds(input: Input): RectF {
        val top = bounds.top + bounds.bottom * input.y.toFloat()
        val bottom = top * input.y.toFloat() + bounds.bottom * input.height.toFloat()

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
