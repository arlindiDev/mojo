package com.mojo.app.render

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.mojo.app.engine.Bounds
import com.mojo.app.engine.RenderObject

class MojoView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var renderObjects: List<RenderObject> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        renderObjects.forEach { objectToRender ->
            val bounds = objectToRender.bounds.toRectF()
            canvas?.drawBackground(bounds, objectToRender.backgroundColor.color())

            objectToRender.media?.let { media ->
                val bitmapBounds = media.bitmapBounds.toRectF()
                canvas?.drawBitmap(media.bitmap, null, bitmapBounds, null)
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

fun Bounds.toRectF() = RectF(left, top, right, bottom)

fun String.color() = Color.parseColor(this)
