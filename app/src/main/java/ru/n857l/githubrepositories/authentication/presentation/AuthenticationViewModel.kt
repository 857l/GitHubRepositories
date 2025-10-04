package ru.n857l.githubrepositories.authentication.presentation

class AuthenticationViewModel(
    private val repository: AuthenticationRepository.Base
) {

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
}