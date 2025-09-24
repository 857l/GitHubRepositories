package ru.n857l.githubrepositories.views.button

import android.view.View
import java.io.Serializable

interface SignInUiState : Serializable {

    fun update(updateSignInButton: UpdateSignInButton)

    abstract class Abstract(
        private val visible: Int
    ) : SignInUiState {

        override fun update(updateSignInButton: UpdateSignInButton) {
            updateSignInButton.update(visible)
        }
    }

    object Visible : Abstract(View.VISIBLE)

    object Gone : Abstract(View.GONE)
}