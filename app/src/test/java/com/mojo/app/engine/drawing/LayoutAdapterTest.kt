package com.mojo.app.engine.drawing

import android.graphics.Bitmap
import com.google.common.truth.Truth.assertThat
import com.mojo.app.data.Layout
import com.mojo.app.engine.RenderObject
import com.mojo.app.engine.LayoutAdapter
import com.mojo.app.engine.Bounds
import com.mojo.app.engine.MediaObject
import com.mojo.app.engine.media.ImageFetcher
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.io.InputStream

class LayoutAdapterTest {
    val bitmap = mock<Bitmap> {
        on { width } doReturn 100
        on { height } doReturn 100
    }

    val imageFetcher = mock<ImageFetcher> {
        on { bitmapFromResource(any()) } doReturn mock
        on { resize(any(), any()) } doReturn mock
        on { centerCrop() } doReturn mock
        on { get() } doReturn bitmap
    }

    @Test
    fun `should adapt 3 nested children`() = runBlocking {
        val subject = LayoutAdapter(
            tolayout("/test-layout-1.json"),
            imageFetcher
        )

        subject.adapt(1000, 1000).collect { result ->
            val fakeAdaptedResult = listOf(
                RenderObject(
                    Bounds(0.0f, 0.0f, 1000.0f, 1000.0f),
                    "#000000"
                ),
                RenderObject(
                    Bounds(
                        100.0f, 100.0f, 900.0f, 900.0f
                    ),
                    "#73D3A2"
                ),
                RenderObject(
                    Bounds(100.0f, 100.0f, 500.0f, 500.0f),
                    "#cecece",
                    MediaObject(
                        bitmap,
                        bitmapBounds=Bounds(left=100.0f, top=100.0f, right=500.0f, bottom=500.0f)
                    )
                )
            )

            assertThat(result).isEqualTo(fakeAdaptedResult)
        }
    }

    @Test
    fun `should adapt 3 nested children with anchor_x=center and anchor_y=center`() = runBlocking {
        val subject = LayoutAdapter(
            tolayout("/test-layout-2.json"),
            imageFetcher
        )

        subject.adapt(1000, 1000).collect { result ->
            val fakeAdaptedResult = listOf(
                RenderObject(
                    Bounds(0.0f, 0.0f, 1000.0f, 1000.0f),
                    "#000000"
                ),
                RenderObject(
                    Bounds(left = -300.0f, top = -300.0f, right = 500.0f, bottom = 500.0f),
                    "#73D3A2"
                ),
                RenderObject(
                    Bounds(left = -500.0f, top = -500.0f, right = -100.0f, bottom = -100.0f),
                    "#cecece"
                )
            )

            assertThat(result).isEqualTo(fakeAdaptedResult)
        }

    }

    @Test
    fun `should adapt 2 nested children with anchor_x=center and anchor_y=center`() = runBlocking {
        val subject = LayoutAdapter(
            tolayout("/test-layout-3.json"),
            imageFetcher
        )

        subject.adapt(1000, 1000).collect { result ->
            val fakeAdaptedResult = listOf(
                RenderObject(
                    Bounds(0.0f, 0.0f, 1000.0f, 1000.0f),
                    "#000000"
                ),
                RenderObject(
                    Bounds(left = 100.0f, top = 100.0f, right = 900.0f, bottom = 900.0f),
                    "#73D3A2"
                ),
                RenderObject(
                    Bounds(left = 180.0f, top = 420.0f, right = 820.0f, bottom = 580.0f),
                    "#6BA2F7"
                )
            )

            assertThat(result).isEqualTo(fakeAdaptedResult)
        }
    }


    @Test
    fun `should adapt 2 children with anchor_x=right and anchor_y=bottom`() = runBlocking {
        val subject = LayoutAdapter(
            tolayout("/test-layout-4.json"),
            imageFetcher
        )

        subject.adapt(1000, 1000).collect { result ->
            val fakeAdaptedResult = listOf(
                RenderObject(
                    Bounds(0.0f, 0.0f, 1000.0f, 1000.0f),
                    "#000000"
                ),
                RenderObject(
                    Bounds(left = -300.0f, top = 500.0f, right = 100.0f, bottom = 900.0f),
                    "#cecece"
                ),
            )

            assertThat(result).isEqualTo(fakeAdaptedResult)
        }
    }

    @Test
    fun `should adapt 1 child anchor_x=center and anchor_y=center, and 2 children one with default anchor, the other anchor_x=right`() =
        runBlocking {
            val subject = LayoutAdapter(
                tolayout("/test-layout-5.json"),
                imageFetcher
            )

            subject.adapt(1000, 1000).collect { result ->
                val fakeAdaptedResult = listOf(
                    RenderObject(
                        Bounds(left = 0.0f, top = 0.0f, right = 1000.0f, bottom = 1000.0f),
                        "#6BA2F7"
                    ),
                    RenderObject(
                        Bounds(left = 100.0f, top = 100.0f, right = 900.0f, bottom = 900.0f),
                        "#73D3A2"
                    ),
                    RenderObject(
                        Bounds(left = 180.0f, top = 420.0f, right = 820.0f, bottom = 580.0f),
                        "#6BA2F7"
                    ),
                    RenderObject(
                        Bounds(left = 244.0f, top = 436.0f, right = 468.0f, bottom = 564.0f),
                        "#73D3A2"
                    ),
                    RenderObject(
                        Bounds(left = 532.0f, top = 436.0f, right = 756.0f, bottom = 564.0f),
                        "#73D3A2"
                    )
                )

                assertThat(result).isEqualTo(fakeAdaptedResult)
            }
        }
}

fun Any.tolayout(resourceName: String): Layout {
    val moshi: Moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<Layout> = moshi.adapter(Layout::class.java)

    val layout = javaClass.getResourceAsStream(resourceName)!!.readlayoutStream()
    return jsonAdapter.fromJson(layout)!!
}

fun InputStream.readlayoutStream(): String {
    use {
        val bytes = readBytes()
        return String(bytes, 0, bytes.size, Charsets.UTF_8)
    }
}
