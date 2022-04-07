package com.mojo.app.drawing

import com.mojo.app.data.Layout
import com.mojo.app.data.LeftAnchor
import com.mojo.app.data.RightAnchor
import kotlin.math.min

interface Anchor {
    fun toParentBounds(layout: Layout): Rect
}

class Left(private val bounds: Rect) : Anchor {
    override fun toParentBounds(layout: Layout): Rect {
        val width = bounds.right - bounds.left

        val left = bounds.left + width * layout.x.toFloat()
        val right = min(left + width * layout.width.toFloat(), bounds.right)

        return bounds.copy(
            left = left,
            right = right
        )
    }
}

class Top(private val bounds: Rect) : Anchor {
    override fun toParentBounds(layout: Layout): Rect {
        val height = bounds.bottom - bounds.top

        val top = bounds.top + height * layout.y.toFloat()
        val bottom = min(top + height * layout.height.toFloat(), bounds.bottom)

        return bounds.copy(
            top = top,
            bottom = bottom
        )
    }
}

class CenterX(private val bounds: Rect) : Anchor {
    override fun toParentBounds(layout: Layout): Rect {
        val width = bounds.right - bounds.left

        val left = bounds.left + width * layout.x.toFloat()
        val right = left + width * layout.width.toFloat()

        val center = (right - left) / 2
        return bounds.copy(
            left = left - center,
            right = right - center,
        )
    }
}

class CenterY(private val bounds: Rect) : Anchor {
    override fun toParentBounds(layout: Layout): Rect {
        val height = bounds.bottom - bounds.top

        val top = bounds.top + height * layout.y.toFloat()
        val bottom = top + height * layout.height.toFloat()

        val center = (bottom - top) / 2
        return bounds.copy(
            top = top - center,
            bottom = bottom - center
        )
    }
}

class Right(private val bounds: Rect) : Anchor {
    override fun toParentBounds(layout: Layout): Rect {
        val width = bounds.right - bounds.left

        val left = bounds.left - width * layout.width.toFloat() + width * layout.x.toFloat()
        val right = bounds.left + width * layout.x.toFloat()

        return bounds.copy(
            right = right,
            left = left
        )
    }
}

class Bottom(private val bounds: Rect) : Anchor {
    override fun toParentBounds(layout: Layout): Rect {
        val height = bounds.bottom - bounds.top

        val top = bounds.bottom - height * layout.height.toFloat() - height * layout.y.toFloat()
        val bottom = bounds.bottom - height * layout.y.toFloat()

        return bounds.copy(
            top = top,
            bottom = bottom
        )
    }
}

fun Rect.toAnchor(layout: Layout): Rect {
    val anchorX = when (layout.anchorX) {
        LeftAnchor.left -> Left(this)
        LeftAnchor.center -> CenterX(this)
        LeftAnchor.right -> Right(this)
    }

    val anchorY = when (layout.anchorY) {
        RightAnchor.top -> Top(this)
        RightAnchor.center -> CenterY(this)
        RightAnchor.bottom -> Bottom(this)
    }

    val anchorXBounds = anchorX.toParentBounds(layout)
    val anchorYBounds = anchorY.toParentBounds(layout)

    return anchorXBounds.copy(top = anchorYBounds.top, bottom = anchorYBounds.bottom)
}
