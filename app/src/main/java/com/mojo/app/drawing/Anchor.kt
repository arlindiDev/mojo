package com.mojo.app.drawing

import android.graphics.RectF
import com.mojo.app.Input
import com.mojo.app.LeftAnchor
import com.mojo.app.RightAnchor
import com.mojo.app.copy

interface Anchor {
    fun toParentBounds(input: Input): RectF
}

class Left(private val bounds: RectF) : Anchor {
    override fun toParentBounds(input: Input): RectF {
        val left = bounds.left + bounds.right * input.x.toFloat()
        val right = left + bounds.right * input.width.toFloat()

        return bounds.copy(
            left = left,
            right = right
        )
    }
}

class Bottom(private val bounds: RectF) : Anchor {
    override fun toParentBounds(input: Input): RectF {
        val top = bounds.top + bounds.bottom * input.y.toFloat()
        val bottom = top + bounds.bottom * input.height.toFloat()

        return bounds.copy(
            top = top,
            bottom = bottom
        )
    }
}

fun RectF.toAnchor(input: Input): RectF {
    val anchorX = when (input.anchorX) {
        LeftAnchor.left -> Left(this)
        else -> Left(this)
    }

    val anchorY = when (input.anchorY) {
        RightAnchor.bottom -> Bottom(this)
        else -> Bottom(this)
    }

    val anchorXBounds = anchorX.toParentBounds(input)
    val anchorYBounds = anchorY.toParentBounds(input)

    return anchorXBounds.copy(top = anchorYBounds.top, bottom = anchorYBounds.bottom)
}

fun Double.invert(): Float {
    return (1 - this).toFloat()
}
