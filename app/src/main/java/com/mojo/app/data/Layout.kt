package com.mojo.app.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Layout(
    val width: Double = 1.0,
    val height: Double = 1.0,
    val x: Double = 0.0,
    val y: Double = 0.0,
    @Json(name="anchor_x")
    val anchorX: LeftAnchor = LeftAnchor.left,
    @Json(name="anchor_y")
    val anchorY: RightAnchor = RightAnchor.bottom,
    @Json(name="background_color")
    val backgroundColor: String,
    val padding: Double = 0.0,
    val media: String? = null,
    @Json(name="media_content_mode")
    val mediaContentMode: MediaContentMode? = null,
    val children: List<Layout> = emptyList()
)

enum class MediaContentMode {
    fill, fit
}

enum class LeftAnchor {
    left, center, right
}

enum class RightAnchor {
    bottom, center, top
}
