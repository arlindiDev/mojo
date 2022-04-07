package com.mojo.app.matchers

import android.content.Context
import android.view.View
import com.mojo.app.helpers.bitmapFrom
import com.mojo.app.helpers.toBitmap
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun hasBitmap(context: Context, imageFileName: String) = object : TypeSafeMatcher<View>() {
    override fun matchesSafely(view: View): Boolean {
        val bitmap = bitmapFrom(view)
        val expectedBitmap = toBitmap("$imageFileName.png", context.resources.assets)

        val areBitmapsTheSame = bitmap.sameAs(expectedBitmap)
        if (!areBitmapsTheSame) {
            return false
        }

        return true
    }

    override fun describeTo(description: Description) {
        description.appendText("The rendered image should be the same as $imageFileName")
    }
} as Matcher<View>
