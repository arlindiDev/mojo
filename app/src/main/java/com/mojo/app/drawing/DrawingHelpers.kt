package com.mojo.app.drawing

import android.graphics.Color
import android.graphics.RectF

fun String.color() = Color.parseColor(this)

fun Double.invert(): Float {
    return (1 - this).toFloat()
}

data class Rect(val left: Float, val top: Float, val right: Float, val bottom: Float)

fun Rect.toRectF() = RectF(left, top, right, bottom)
