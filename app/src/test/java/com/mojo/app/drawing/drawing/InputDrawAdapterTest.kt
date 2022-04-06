package com.mojo.app.drawing.drawing

import com.google.common.truth.Truth.assertThat
import com.mojo.app.Input
import com.mojo.app.drawing.InputDraw
import com.mojo.app.drawing.InputDrawAdapter
import com.mojo.app.drawing.Rect
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Test
import java.io.InputStream

class InputDrawAdapterTest {

    @Test
    fun `should adapt 3 nested children`() {
        val subject = InputDrawAdapter(input = toInput("/test-input-1.json"))

        val result = subject.adapt(1000.0f, 1000.0f)

        val fakeAdaptedResult = listOf(
            InputDraw(
                bounds = Rect(0.0f, 0.0f, 1000.0f, 1000.0f),
                "#000000"
            ),
            InputDraw(
                bounds = Rect(
                    100.0f, 100.0f, 900.0f, 900.0f
                ), backgroundColor = "#73D3A2"
            ),
            InputDraw(
                bounds = Rect(100.0f, 100.0f, 500.0f, 500.0f),
                backgroundColor = "#cecece"
            )
        )

        assertThat(result).isEqualTo(fakeAdaptedResult)
    }

    @Test
    fun `should adapt 3 nested children with anchor_x=center and anchor_y=center`() {
        val subject = InputDrawAdapter(input = toInput("/test-input-2.json"))

        val result = subject.adapt(1000.0f, 1000.0f)

        val fakeAdaptedResult = listOf(
            InputDraw(
                Rect(0.0f, 0.0f, 1000.0f, 1000.0f),
                "#000000"
            ),
            InputDraw(
                Rect(left = -300.0f, top = -300.0f, right = 500.0f, bottom = 500.0f),
                "#73D3A2"
            ),
            InputDraw(
                Rect(left = -500.0f, top = -500.0f, right = -100.0f, bottom = -100.0f),
                "#cecece"
            )
        )

        assertThat(result).isEqualTo(fakeAdaptedResult)
    }

    @Test
    fun `should adapt 2 nested children with anchor_x=center and anchor_y=center`() {
        val subject = InputDrawAdapter(input = toInput("/test-input-3.json"))

        val result = subject.adapt(1000.0f, 1000.0f)

        val fakeAdaptedResult = listOf(
            InputDraw(
                Rect(0.0f, 0.0f, 1000.0f, 1000.0f),
                "#000000"
            ),
            InputDraw(
                Rect(left = 100.0f, top = 100.0f, right = 900.0f, bottom = 900.0f),
                "#73D3A2"
            ),
            InputDraw(
                Rect(left = 180.0f, top = 420.0f, right = 820.0f, bottom = 580.0f),
                "#6BA2F7"
            )
        )

        assertThat(result).isEqualTo(fakeAdaptedResult)
    }
}

fun Any.toInput(resourceName: String): Input {
    val moshi: Moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<Input> = moshi.adapter(Input::class.java)

    val input = javaClass.getResourceAsStream(resourceName)!!.readInputStream()
    return jsonAdapter.fromJson(input)!!
}

fun InputStream.readInputStream(): String {
    use {
        val bytes = readBytes()
        return String(bytes, 0, bytes.size, Charsets.UTF_8)
    }
}
