package com.mojo.app.engine.media

import android.graphics.Bitmap
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

interface ImageFetcher {
    fun bitmapFromResource(resourceName: String): ImageFetcher
    fun resize(width: Float, height: Float): ImageFetcher
    fun centerCrop(): ImageFetcher
    fun get(): Bitmap
}

class DefaultImageFetcher(val resourceIdForName: (String) -> Int) : ImageFetcher {
    private val picasso = Picasso.get()

    private lateinit var imageRequest: RequestCreator

    override fun bitmapFromResource(resourceName: String) = apply {
        imageRequest = picasso.load(resourceIdForName(resourceName))
    }

    override fun resize(width: Float, height: Float) = apply {
        imageRequest = imageRequest.resize(width.toInt(), height.toInt())
    }

    override fun centerCrop() = apply {
        imageRequest.centerCrop()
    }

    override fun get(): Bitmap {
        return imageRequest.get()!!
    }
}
