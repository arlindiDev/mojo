package com.mojo.app.drawing

import android.graphics.Color
import android.graphics.RectF

fun RectF.copy(
    left: Float = this.left,
    top: Float = this.top,
    right: Float = this.right,
    bottom: Float = this.bottom,
) = RectF(left, top, right, bottom)

fun String.color() = Color.parseColor(this)

fun Double.invert(): Float {
    return (1 - this).toFloat()
}
