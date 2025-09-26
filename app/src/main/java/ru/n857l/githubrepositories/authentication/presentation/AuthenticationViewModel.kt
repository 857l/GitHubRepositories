package ru.n857l.githubrepositories.authentication.presentation

class AuthenticationViewModel(
    private val repository: AuthenticationRepository.Base
) {

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