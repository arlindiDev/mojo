package com.mojo.app

data class Input(
    val width: Double,
    val height: Double,
    val x: Double,
    val y: Double,
    val anchorX: String,
    val anchorY: String,
    val backgroundColor: String,
    val padding: Double,
    val children: ArrayList<Input>
)
