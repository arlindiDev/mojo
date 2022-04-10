package com.mojo.app.matchers

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.MotionEvents
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Matcher

fun moveFromTo(fromX: Float, fromY: Float, toX: Float, toY: Float): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isDisplayed()
        }

        override fun getDescription(): String {
            return "Sending touch events."
        }

        override fun perform(uiController: UiController, view: View) {
            val location = IntArray(2)
            view.getLocationOnScreen(location)

            val coordinatesDown = floatArrayOf(fromX + location[0], fromY + location[1])
            val precision = floatArrayOf(1f, 1f)

            val down = MotionEvents.sendDown(uiController, coordinatesDown, precision).down
            uiController.loopMainThreadForAtLeast(300)
            MotionEvents.sendUp(uiController, down, coordinatesDown)

            val downTwice = MotionEvents.sendDown(uiController, coordinatesDown, precision).down
            uiController.loopMainThreadForAtLeast(300)

            val coordinatesMove = floatArrayOf(toX + location[0], toY + location[1])
            MotionEvents.sendMovement(uiController, downTwice, coordinatesMove)

            uiController.loopMainThreadForAtLeast(300)
            MotionEvents.sendUp(uiController, downTwice, coordinatesMove)
        }
    }
}
