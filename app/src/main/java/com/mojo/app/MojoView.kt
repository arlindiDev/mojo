package com.mojo.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.mojo.app.drawing.color
import com.mojo.app.drawing.toAnchor
import com.mojo.app.drawing.withPadding

class MojoView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var input: Input = defaultInput
        set(value) {
            field = value
            invalidate()
        }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        val bounds = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())
        canvas?.drawBackground(bounds, Color.BLACK)

        canvas?.drawInput(bounds, input)
    }

    private fun Canvas.drawInput(bounds: RectF, input: Input) {
        val canvas = this

        with(input) {
            Log.i("ARLINDO", "bounds ${bounds.toShortString()} ${input.backgroundColor}")
            val parentBounds = bounds.toAnchor(input)
            Log.i("ARLINDO", "parent ${parentBounds.toShortString()} ${input.backgroundColor}")

            canvas.drawBackground(
                parentBounds,
                backgroundColor.color()
            )

            children.forEachIndexed { index, childInput ->
                val childBounds = parentBounds.withPadding(input.padding)

                drawInput(childBounds, childInput)
            }
        }
    }

    private fun Canvas.drawBackground(bounds: RectF, backgroundColor: Int) {
        val paint = Paint().apply {
            color = backgroundColor
            style = Paint.Style.FILL
        }

        drawRect(bounds, paint)
    }
}
