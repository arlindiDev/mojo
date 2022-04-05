package com.mojo.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MojoView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var input: Input = defaultInput
        set(value) {
            field = value
            invalidate()
        }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawBackground()
    }

    private fun Canvas.drawBackground() {
        val paint = Paint().apply {
            color = input.backgroundColor.color()
            style = Paint.Style.FILL
        }

        drawPaint(paint)
    }
}

fun String.color() = Color.parseColor(this)
