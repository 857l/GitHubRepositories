package ru.n857l.githubrepositories.authentication

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher

class SignInButtonUi(
    id: Int,
    @StringRes textResId: Int,
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) : AbstractButtonUi(
    onView(
        allOf(
            withId(id),
            withText(textResId),
            containerIdMatcher,
            containerClassTypeMatcher,
            isAssignableFrom(AppCompatButton::class.java)
        )
    )
)

abstract class AbstractButtonUi(
    protected val interaction: ViewInteraction
) {
    fun click() {
        interaction.perform(androidx.test.espresso.action.ViewActions.click())
    }

    fun assertEnabled() {
        interaction.check(matches(isEnabled()))
    }

    fun assertNotEnabled() {
        interaction.check(matches(not(isEnabled())))
    }
}