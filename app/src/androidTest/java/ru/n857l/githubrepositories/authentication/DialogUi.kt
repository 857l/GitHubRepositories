package ru.n857l.githubrepositories.authentication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import ru.n857l.githubrepositories.R


class ErrorDialogUi {

    private val messageView = onView(
        allOf(
            withId(R.id.error_description)
        )
    ).inRoot(isDialog())

    fun assertMessage(text: String) {
        messageView.check(matches(withText(text)))
    }

    fun assertVisible() {
        messageView.check(matches(isDisplayed()))
    }
}