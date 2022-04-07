package com.mojo.app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.mojo.app.helpers.readLayoutFrom
import com.mojo.app.helpers.launch
import com.mojo.app.helpers.Size
import com.mojo.app.matchers.hasBitmap
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ScreenshotTests {

    val context = getInstrumentation().context
    val application = (getInstrumentation().targetContext.applicationContext as TestMojoApplication)

    @Test
    fun shouldImageBeTheSameAsLayout1() {
        val layoutFileName = "test-layout-1"
        application.layout = readLayoutFrom(context, layoutFileName)

        launch(Size(1024, 1024)) {
            onView(withId(R.id.mojoView))
                .check(matches(hasBitmap(context, layoutFileName)))
        }
    }

    @Test
    fun shouldImageBeTheSameAsLayout2() {
        val layoutFileName = "test-layout-2"
        application.layout = readLayoutFrom(context, layoutFileName)

        launch(Size(1024, 1024)) {
            onView(withId(R.id.mojoView))
                .check(matches(hasBitmap(context, layoutFileName)))
        }
    }

    @Test
    fun shouldImageBeTheSameAsLayout3() {
        val layoutFileName = "test-layout-3"
        application.layout = readLayoutFrom(context, layoutFileName)

        launch(Size(1024, 1024)) {
            onView(withId(R.id.mojoView))
                .check(matches(hasBitmap(context, layoutFileName)))
        }
    }

    @Test
    fun shouldImageBeTheSameAsLayout4() {
        val layoutFileName = "test-layout-4"
        application.layout = readLayoutFrom(context, layoutFileName)

        launch(Size(1024, 1024)) {
            onView(withId(R.id.mojoView))
                .check(matches(hasBitmap(context, layoutFileName)))
        }
    }

    @Test
    fun testTheRealLayoutFromTheTest() {
        val layoutFileName = "the-layout-from-the-test" // the JSON layout file name from "androidTest/assets/"
        application.layout = readLayoutFrom(context, layoutFileName) // sets the JSON layout on the app

        launch(Size(1024, 1024)) { // we can test different screen sized
            onView(withId(R.id.mojoView))
                .check(matches(hasBitmap(context, layoutFileName))) // compares the MojoView Bitmap with the test PNG from "androidTest/assets/"
        }
    }
}