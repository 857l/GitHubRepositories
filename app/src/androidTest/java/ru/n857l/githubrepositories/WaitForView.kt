package ru.n857l.githubrepositories

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

fun waitForView(viewMatcher: Matcher<View>, timeout: Long = 5000): ViewAction =
    object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()

        override fun getDescription(): String =
            "wait for a specific view <$viewMatcher> to appear within $timeout ms."

        override fun perform(uiController: UiController, view: View?) {
            val startTime = System.currentTimeMillis()
            val endTime = startTime + timeout

            do {
                val currentTime = System.currentTimeMillis()
                val found = try {
                    val view = view?.rootView
                    view?.findViewByMatcher(viewMatcher) != null
                } catch (e: Exception) {
                    false
                }

                if (found) return

                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)

            throw PerformException.Builder()
                .withCause(TimeoutException())
                .withActionDescription(description)
                .build()
        }
    }

private fun View.findViewByMatcher(matcher: Matcher<View>): View? {
    if (matcher.matches(this)) return this
    if (this is ViewGroup) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val match = child.findViewByMatcher(matcher)
            if (match != null) return match
        }
    }
    return null
}