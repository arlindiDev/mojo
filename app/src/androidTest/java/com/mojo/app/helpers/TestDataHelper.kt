package com.mojo.app.helpers

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import com.mojo.app.MainActivity
import kotlinx.android.synthetic.main.activity_main.mojoView

inline fun launch(
    size: Size,
    crossinline assert: () -> Unit
) {
    launchActivity<MainActivity>().use { scenario ->
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.onActivity { activity ->
            activity.mojoView.layoutParams =
                ConstraintLayout.LayoutParams(size.width, size.height)
        }
        scenario.moveToState(Lifecycle.State.RESUMED)
        assert()
    }
}


data class Size(val width: Int, val height: Int)

fun readLayoutFrom(context: Context, layoutFileName: String) =
    context.resources.assets.open("$layoutFileName.json")
        .bufferedReader().use { it.readText() }

