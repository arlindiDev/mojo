package com.mojo.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.mojo.app.drawing.InputDrawAdapter
import com.mojo.app.drawing.color
import com.mojo.app.drawing.defaultInputDrawAdapter
import com.mojo.app.drawing.toRectF

class MojoView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var inputAdapter: InputDrawAdapter = defaultInputDrawAdapter
        set(value) {
            field = value
            invalidate()
        }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        val inputs = inputAdapter.adapt(width.toFloat(), height.toFloat())

        inputs.forEach { input ->
            canvas?.drawBackground(input.bounds.toRectF(), input.backgroundColor.color())
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
