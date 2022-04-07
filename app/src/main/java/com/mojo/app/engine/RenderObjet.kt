package com.mojo.app.engine

import android.graphics.Bitmap

data class RenderObjet(
    val bounds: Bounds,
    val backgroundColor: String,
    val media: Media? = null
)

data class Media(
    val bitmap: Bitmap,
    val bitmapBounds: Bounds,
)
