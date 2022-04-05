package com.mojo.app.drawing

import com.mojo.app.Input
import com.mojo.app.defaultInput

class InputDrawAdapter(private val input: Input) {
    private lateinit var flattenInput: MutableList<InputDraw>

    fun adapt(width: Float, height: Float): List<InputDraw> {
        flattenInput = mutableListOf()

        val startingBounds = Rect(0.0f, 0.0f, width, height)

        drawInput(startingBounds, input)

        return flattenInput.toList()
    }

    private fun drawInput(bounds: Rect, input: Input) {
        val parentBounds = bounds.toAnchor(input)

        with(input) {
            flattenInput.add(InputDraw(parentBounds, backgroundColor))

            children.forEachIndexed { index, childInput ->
                val childBounds = parentBounds.withPadding(input.padding)

                drawInput(childBounds, childInput)
            }
        }
    }
}

val defaultInputDrawAdapter = InputDrawAdapter(defaultInput)

data class InputDraw(val bounds: Rect, val backgroundColor: String)
