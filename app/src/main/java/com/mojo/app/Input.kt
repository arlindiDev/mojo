package com.mojo.app

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Input(
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
    val children: List<Input> = emptyList()
)

enum class LeftAnchor {
    left, center, right
}

enum class RightAnchor {
    bottom, center, top
}

val defaultInput = Input(
    backgroundColor = "#fff"
)
