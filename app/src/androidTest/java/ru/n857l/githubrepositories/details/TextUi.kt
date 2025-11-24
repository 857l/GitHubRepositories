package ru.n857l.githubrepositories.details

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.Matchers.isEmptyString

class TextViewUi(
    id: Int,
    containerIdMatcher: Matcher<View>,
) : AbstractTextUi(
    onView(
        allOf(
            withId(id),
            isDescendantOfA(containerIdMatcher),
            isAssignableFrom(TextView::class.java)
        )
    )
)

abstract class AbstractTextUi(
    protected val interaction: ViewInteraction
) {
    fun assertVisible() {
        interaction.check(matches(isDisplayed()))
    }

    fun assertNotEmpty() {
        interaction.check(matches(withText(not(isEmptyString()))))
    }

    fun assertEmpty() {
        interaction.check(matches(withText("")))
    }

    fun assertInitial() {
        assertVisible()
        assertNotEmpty()
    }
}