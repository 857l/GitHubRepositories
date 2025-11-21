package ru.n857l.githubrepositories.authentication

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import org.hamcrest.Matcher
import ru.n857l.githubrepositories.ProgressUi
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.input.InputUi

class AuthenticationPage {

    private val containerIdMatcher: Matcher<View> =
        withParent(withId(R.id.authenticationContainer))
    private val containerClassTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val tokenInputUi = InputUi()

    private val signInUi = SignInButtonUi(
        id = R.id.singInButton,
        textResId = R.string.sing_in,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )

    private val progressUi = ProgressUi(
        containerIdMatcher = containerIdMatcher,
        classTypeMatcher = containerClassTypeMatcher
    )

    fun assertInitialState(inputText: String) {
        tokenInputUi.assertText(inputText)

        if (inputText.isNotEmpty())
            signInUi.assertEnabled()
        else
            signInUi.assertNotEnabled()

        progressUi.assertNotVisible()
    }

    fun assertWrongInputState() {
        tokenInputUi.assertIncorrectState()
        signInUi.assertNotEnabled()
        progressUi.assertNotVisible()
    }

    fun assertSuccessInputState() {
        tokenInputUi.assertCorrectState()
        signInUi.assertEnabled()
        progressUi.assertNotVisible()
    }

    fun assertLoadState() {
        tokenInputUi.assertCorrectState()
        signInUi.assertNotEnabled()
        progressUi.assertVisible()
    }

    fun addInput(text: String) {
        tokenInputUi.addInput(text = text)
    }

    fun clickSignIn() {
        signInUi.click()
    }

    fun removeInputLastLetter() {
        tokenInputUi.removeInputLastLetter()
    }
}