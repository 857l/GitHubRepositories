package ru.n857l.githubrepositories.authentication.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.n857l.githubrepositories.core.ClearViewModel
import ru.n857l.githubrepositories.core.RunAsync
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.di.MyViewModel

class AuthenticationViewModel(
    private val repository: AuthenticationRepository,
    private val clearViewModel: ClearViewModel,
    private val observable: UiObservable<AuthenticationUiState>,
    private val runAsync: RunAsync
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
                AuthenticationUiState.Initial(repository.token())
            else
                AuthenticationUiState.Empty
        )
    }

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun load(isFirstRun: Boolean = true) {

        if (isFirstRun) {
            observable.postUiState(AuthenticationUiState.Load)
            runAsync.handleAsync(viewModelScope, {
                val result = repository.load()
                if (result.isSuccessful()) {
                    clear()
                    AuthenticationUiState.Success
                } else
                    AuthenticationUiState.Error(result.message())
            }) {
                observable.postUiState(it)
            }
        }
    }

    fun clear() {
        clearViewModel.clear(AuthenticationViewModel::class.java)
    }

    fun startUpdates(observer: (AuthenticationUiState) -> Unit) =
        observable.register(observer)

    fun stopUpdates() = observable.unregister()
}