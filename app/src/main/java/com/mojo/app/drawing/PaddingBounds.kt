package com.mojo.app.drawing

import android.graphics.RectF

fun RectF.withPadding(padding: Double): RectF {
    if(padding == 0.0) {
        return this
    }

    val width = right - left
    val height = bottom - top

    return this.copy(
        left = left + width - width * padding.invert(),
        right = right - width * padding.toFloat(),
        top = top + height - height * padding.invert(),
        bottom = bottom - height * padding.toFloat(),
    )
}


