package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.repositories.presentation.NavigateToRepositories
import ru.n857l.githubrepositories.views.input.UpdateInput
import ru.n857l.githubrepositories.views.signInButton.SignInUiState
import ru.n857l.githubrepositories.views.signInButton.UpdateSignInButton
import ru.n857l.githubrepositories.views.visibility.UpdateVisibility
import ru.n857l.githubrepositories.views.visibility.VisibilityUiState
import java.io.Serializable

interface AuthenticationUiState : Serializable {

    fun update(
        tokenInputView: UpdateInput,
        singInButton: UpdateSignInButton,
        progressBar: UpdateVisibility
    ) = Unit

    fun navigate(navigate: NavigateToRepositories) = Unit

    object Empty : AuthenticationUiState

    data class Initial(private val inputText: String) : AuthenticationUiState {

        override fun update(
            tokenInputView: UpdateInput,
            singInButton: UpdateSignInButton,
            progressBar: UpdateVisibility
        ) {
            tokenInputView.update(inputText)
            singInButton.update(SignInUiState.Enabled)
            progressBar.update(VisibilityUiState.Gone)
        }
    }

    object Finish : AuthenticationUiState {
        override fun navigate(navigate: NavigateToRepositories) = navigate.navigateToRepositories()
    }

    object Load : AuthenticationUiState {

        override fun update(
            tokenInputView: UpdateInput,
            singInButton: UpdateSignInButton,
            progressBar: UpdateVisibility
        ) {
            singInButton.update(SignInUiState.NotEnabled)
            progressBar.update(VisibilityUiState.Visible)
        }
    }
}