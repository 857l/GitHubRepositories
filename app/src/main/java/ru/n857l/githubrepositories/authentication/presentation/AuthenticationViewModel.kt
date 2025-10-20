package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.core.ClearViewModel
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.di.MyViewModel

class AuthenticationViewModel(
    private val repository: AuthenticationRepository,
    private val clearViewModel: ClearViewModel,
    private val observable: UiObservable<AuthenticationUiState>
) : MyViewModel {

    fun handleUserInput(text: String) {
        repository.saveUserInput(text)
        observable.postUiState(
            if (repository.tokenIsValid(text))
                AuthenticationUiState.SuccessInput
            else
                AuthenticationUiState.WrongInput
        )
    }

    fun init(isFirstRun: Boolean = true) {
        observable.postUiState(
            if (isFirstRun)
                AuthenticationUiState.Initial(repository.data())
            else
                AuthenticationUiState.Empty
        )
    }

    fun load() {
        clear()
        observable.postUiState(AuthenticationUiState.Finish)
    }

    fun clear() {
        clearViewModel.clear(AuthenticationViewModel::class.java)
    }

    fun startUpdates(observer: (AuthenticationUiState) -> Unit) =
        observable.register(observer)

    fun stopUpdates() = observable.unregister()
}