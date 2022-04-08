package com.mojo.app.engine

data class Bounds(val left: Float, val top: Float, val right: Float, val bottom: Float)

fun emptyBounds() = Bounds(0f, 0f, 0f, 0f)
