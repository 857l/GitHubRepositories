package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.repositories.presentation.NavigateToRepositories
import ru.n857l.githubrepositories.views.input.InputUiState
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

    object WrongInput : AuthenticationUiState {
        override fun update(
            tokenInputView: UpdateInput,
            singInButton: UpdateSignInButton,
            progressBar: UpdateVisibility
        ) {
            tokenInputView.update(InputUiState.Incorrect)
            singInButton.update(SignInUiState.NotEnabled)
        }
    }

    object SuccessInput : AuthenticationUiState {
        override fun update(
            tokenInputView: UpdateInput,
            singInButton: UpdateSignInButton,
            progressBar: UpdateVisibility
        ) {
            tokenInputView.update(InputUiState.Correct)
            singInButton.update(SignInUiState.Enabled)
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