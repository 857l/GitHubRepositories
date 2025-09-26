package ru.n857l.githubrepositories.views.signInButton

import java.io.Serializable

interface SignInUiState : Serializable {

    fun update(updateSignInButton: UpdateSignInButton)

    abstract class Abstract(
        private val enable: Boolean,
    ) : SignInUiState {

        override fun update(updateSignInButton: UpdateSignInButton){
            updateSignInButton.update(enable)
        }
    }

    object Enabled : Abstract(true)

    object NotEnabled : Abstract(false)
}