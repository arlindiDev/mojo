package com.mojo.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.mojo.app.drawing.LayoutAdapter
import com.mojo.app.drawing.color
import com.mojo.app.drawing.defaultLayoutAdapter
import com.mojo.app.drawing.toRectF

class MojoView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var layoutAdapter: LayoutAdapter = defaultLayoutAdapter
        set(value) {
            field = value
            invalidate()
        }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        val renderObjects = layoutAdapter.adapt(width.toFloat(), height.toFloat())

        renderObjects.forEach { objectToRender ->
            canvas?.drawBackground(objectToRender.bounds.toRectF(), objectToRender.backgroundColor.color())
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
