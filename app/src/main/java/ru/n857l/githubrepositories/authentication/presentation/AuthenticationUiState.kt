package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.core.NavigateToErrorDialog
import ru.n857l.githubrepositories.core.NavigateToFrame
import ru.n857l.githubrepositories.errorrepositories.presentation.NavigateToErrorRepositories
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

    fun navigate(navigate: NavigateToFrame) = Unit

    fun showError(navigate: NavigateToErrorDialog) = Unit

    abstract class Abstract(
        private val tokenInputUiState: InputUiState,
        private val singInUiState: SignInUiState,
        private val progressBarUiState: VisibilityUiState
    ) : AuthenticationUiState {
        override fun update(
            tokenInputView: UpdateInput,
            singInButton: UpdateSignInButton,
            progressBar: UpdateVisibility
        ) {
            tokenInputView.update(tokenInputUiState)
            singInButton.update(singInUiState)
            progressBar.update(progressBarUiState)
        }
    }

    object Empty : AuthenticationUiState

    data class Initial(private val inputText: String) : AuthenticationUiState {

        override fun update(
            tokenInputView: UpdateInput,
            singInButton: UpdateSignInButton,
            progressBar: UpdateVisibility
        ) {
            tokenInputView.update(inputText)
            if (inputText.isNotEmpty())
                singInButton.update(SignInUiState.Enabled)
            else
                singInButton.update(SignInUiState.NotEnabled)
            progressBar.update(VisibilityUiState.Gone)
        }
    }

    object WrongInput :
        Abstract(InputUiState.Incorrect, SignInUiState.NotEnabled, VisibilityUiState.Gone)

    object SuccessInput :
        Abstract(InputUiState.Correct, SignInUiState.Enabled, VisibilityUiState.Gone)

    object Success : AuthenticationUiState {
        override fun navigate(navigate: NavigateToFrame) {
            (navigate as NavigateToRepositories).navigateToRepositories()
        }
    }

    object EmptyRepos : AuthenticationUiState {
        override fun navigate(navigate: NavigateToFrame) {
            (navigate as NavigateToErrorRepositories).navigateToErrorRepositories()
        }
    }

    object Load :
        Abstract(InputUiState.Correct, SignInUiState.NotEnabled, VisibilityUiState.Visible)

    data class ShowError(private val message: String) :
        Abstract(InputUiState.Correct, SignInUiState.Enabled, VisibilityUiState.Gone) {
        override fun showError(navigate: NavigateToErrorDialog) {
            navigate.showErrorDialog(message)
        }
    }
}