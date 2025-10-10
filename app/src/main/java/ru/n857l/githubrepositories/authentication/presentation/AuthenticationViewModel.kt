package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.core.ClearViewModel
import ru.n857l.githubrepositories.di.MyViewModel

class AuthenticationViewModel(
    private val repository: AuthenticationRepository,
    private val clearViewModel: ClearViewModel
) : MyViewModel {

    fun handleUserInput(text: String): AuthenticationUiState {
        repository.saveUserInput(text)
        return if (repository.tokenIsValid(text))
            AuthenticationUiState.SuccessInput
        else
            AuthenticationUiState.WrongInput
    }

    fun init(isFirstRun: Boolean = true): AuthenticationUiState {
        return if (isFirstRun) {
            val inputText = repository.data()
            AuthenticationUiState.Initial(inputText)
        } else {
            AuthenticationUiState.Empty
        }
    }

    fun clear() {
        clearViewModel.clear(AuthenticationViewModel::class.java)
    }
}