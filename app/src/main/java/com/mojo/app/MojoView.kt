package com.mojo.app

import android.content.Context
import android.graphics.*
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

        val bounds = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())

        canvas?.drawInput(bounds, input)
    }

    private fun Canvas.drawInput(bounds: RectF, input: Input) {
        val canvas = this
        with(input) {
            canvas.drawBackground(bounds, backgroundColor.color())

            for (child in children) {
                val childBounds = bounds.toChildBounds(padding.toFloat())

                drawInput(childBounds, child)
            }
        }
    }

    private fun RectF.toChildBounds(padding: Float) = RectF(
        left + right * padding,
        top + bottom * padding,
        right - right * padding,
        bottom - bottom * padding
    )

    private fun Float.add(padding: Float): Float {
        return this + this * padding
    }

    private fun Float.subtract(padding: Float): Float {
        return this - this * padding
    }

    private fun Canvas.drawBackground(bounds: RectF, backgroundColor: Int) {
        val paint = Paint().apply {
            color = backgroundColor
            style = Paint.Style.FILL
        }

        drawRect(bounds, paint)
    }
}

fun String.color() = Color.parseColor(this)
