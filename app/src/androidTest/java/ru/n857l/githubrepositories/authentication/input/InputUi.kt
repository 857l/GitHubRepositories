package ru.n857l.githubrepositories.authentication.input

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.CoreMatchers.allOf
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.authentication.TextInputLayoutHasErrorText

class InputUi {
    private val inputLayoutId: Int = R.id.inputLayout
    private val layoutInteraction: ViewInteraction = onView(
        allOf(
            isAssignableFrom(TextInputLayout::class.java),
            withId(inputLayoutId)
        )
    )

    private val inputInteraction: ViewInteraction = onView(
        allOf(
            isAssignableFrom(TextInputEditText::class.java),
            withId(R.id.tokenInputEditText)
        )
    )

    private val textInputLayoutErrorEnabledMatcherFalse = TextInputLayoutErrorEnabledMatcher(false)

    fun assertText(text: String) {
        inputInteraction.check(matches(withText(text)))
    }

    fun assertInitialState() {
        layoutInteraction.check(matches(isEnabled()))
            .check(matches(textInputLayoutErrorEnabledMatcherFalse))
        inputInteraction.check(matches(withText("")))
    }

    fun addInput(text: String) {
        inputInteraction.perform(replaceText(text), closeSoftKeyboard())
    }

    fun assertCorrectState() {
        layoutInteraction.check(matches(isEnabled()))
            .check(matches(textInputLayoutErrorEnabledMatcherFalse))
    }

    fun assertIncorrectState() {
        layoutInteraction.check(matches(isEnabled()))
            .check(matches(TextInputLayoutErrorEnabledMatcher(true)))
            .check(matches(TextInputLayoutHasErrorText(R.string.incorrect_message)))
    }

    fun removeInputLastLetter() {
        inputInteraction.perform(click(), pressKey(KeyEvent.KEYCODE_DEL), closeSoftKeyboard())
    }
}