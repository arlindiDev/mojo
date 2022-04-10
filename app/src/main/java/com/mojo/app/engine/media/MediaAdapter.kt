package com.mojo.app.engine

import android.graphics.Bitmap
import com.mojo.app.data.MediaContentMode
import com.mojo.app.engine.media.ImageFetcher
import kotlin.math.max
import kotlin.math.min

fun adaptMedia(
    parentBounds: Bounds,
    mediaContentMode: MediaContentMode,
    imageFetcher: ImageFetcher
): MediaObject {
    return when (mediaContentMode) {
        MediaContentMode.fill -> fill(parentBounds, imageFetcher)
        MediaContentMode.fit -> fit(parentBounds, imageFetcher)
    }
}

fun fill(bounds: Bounds, imageFetcher: ImageFetcher): MediaObject {
    val bitmap = imageFetcher.get()

    val width = bounds.right - bounds.left
    val height = bounds.bottom - bounds.top

    val scaleWidth = width / bitmap.width
    val scaleHeight = height / bitmap.height
    val scaleRatio = max(scaleHeight, scaleWidth)

    val scaledBitmap = imageFetcher
        .resize(bitmap.width * scaleRatio, bitmap.height * scaleRatio)
        .get()

    var top = 0f
    var bottom = height
    var left = 0f
    var right = width

    if(scaleHeight > scaleWidth) {
        left =  scaledBitmap.width / 2 - width / 2
        right = left + width
    } else {
        top = scaledBitmap.height / 2 - height / 2
        bottom = top + height
    }


    val crop = Bounds(left, top, right, bottom)

    return MediaObject(scaledBitmap, bounds, crop)
}

fun fit(bounds: Bounds, imageFetcher: ImageFetcher): MediaObject {
    val bitmap = imageFetcher
        .get()

    val scaleWidth = (bounds.right - bounds.left) / bitmap.width
    val scaleHeight = (bounds.bottom - bounds.top) / bitmap.height

    val scaleRatio = min(scaleHeight, scaleWidth)

    val width = bitmap.width * scaleRatio
    val height = bitmap.height * scaleRatio

    val scaledBitmap = imageFetcher
        .resize(width, height)
        .get()

    val top = (bounds.bottom + bounds.top) / 2 - scaledBitmap.height / 2
    val bottom = top + scaledBitmap.height
    val left = (bounds.left + bounds.right) / 2 - scaledBitmap.width / 2
    val right = left + scaledBitmap.width

    return MediaObject(scaledBitmap, Bounds(left, top, right, bottom))
}
