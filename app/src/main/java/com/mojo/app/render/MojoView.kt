package com.mojo.app.render

import android.content.Context
import android.graphics.*
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
                val crop = media.crop.toRect()
                canvas?.drawBitmap(media.bitmap, crop, bitmapBounds, null)
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
fun Bounds?.toRect() = this?.let {
    Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
}

fun String.color() = Color.parseColor(this)
