package com.mojo.app.drawing

import android.graphics.RectF

fun RectF.withPadding(padding: Double): RectF {
    val width = right - left
    val height = bottom - top

    return this.copy(
        left = width - width * padding.invert(),
        right = width * padding.invert(),
        top = height - height * padding.invert(),
        bottom = height * padding.invert(),
    )
}


