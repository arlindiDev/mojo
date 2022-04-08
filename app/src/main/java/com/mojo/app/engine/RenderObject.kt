package com.mojo.app.engine

import android.graphics.Bitmap

data class RenderObject(
    val bounds: Bounds,
    val backgroundColor: String,
    val media: MediaObject? = null
)

data class MediaObject(
    val bitmap: Bitmap,
    val bitmapBounds: Bounds,
    val crop: Bounds? = null
)
