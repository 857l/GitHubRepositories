package ru.n857l.githubrepositories.authentication.presentation

class AuthenticationViewModel(
    private val repository: AuthenticationRepository.Base
) {

    fun tokenIsValid(text: String): Boolean {
        val regex = Regex("^[A-Za-z0-9]+$")
        return regex.matches(text)
    }

    fun handleUserInput(text: String): AuthenticationUiState {
        repository.saveUserInput(text)
        return if (tokenIsValid(text))
            AuthenticationUiState.SuccessInput
        else
            AuthenticationUiState.WrongInput
    }

    fun init(isFirstRun: Boolean): AuthenticationUiState {
        return if (isFirstRun) {
            val inputText = repository.data()
            repository.clear()
            AuthenticationUiState.Initial(inputText)
        } else {
            AuthenticationUiState.Empty
        }
    }
}