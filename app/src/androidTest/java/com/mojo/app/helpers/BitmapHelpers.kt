package com.mojo.app.helpers

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.View

fun bitmapFrom(view: View): Bitmap {
    with(view) {
        val bitmap =
            Bitmap.createBitmap(layoutParams.width, layoutParams.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)

        return bitmap
    }
}

fun toBitmap(imageFileName: String, assetManager: AssetManager): Bitmap {
    val inputStream = assetManager.open(imageFileName)

    return BitmapFactory.decodeStream(inputStream)
}
