package ru.n857l.githubrepositories.authentication.presentation

class AuthenticationViewModel(
    private val repository: AuthenticationRepository
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
            repository.clear()
            handleUserInput(inputText)
        } else {
            AuthenticationUiState.Empty
        }
    }
}