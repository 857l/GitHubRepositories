package ru.n857l.githubrepositories.repositories.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.n857l.githubrepositories.core.RunAsync
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.core.di.ClearViewModel
import ru.n857l.githubrepositories.di.MyViewModel

class RepositoriesViewModel(
    private val repository: RepositoriesRepository,
    private val clearViewModel: ClearViewModel,
    private val observable: UiObservable<RepositoriesUiState>,
    private val runAsync: RunAsync
) : MyViewModel {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init(isFirstRun: Boolean = true) {

        runAsync.handleAsync(viewModelScope, {
            val data = repository.data()
            if (data.isEmpty()) {
                clearViewModel.clear(RepositoriesViewModel::class.java)
                RepositoriesUiState.EmptyRepositories
            } else {
                RepositoriesUiState.ShowList(data)
            }
        }) {
            observable.postUiState(it)
        }
    }

    override fun clear() {
        clearViewModel.clear(RepositoriesViewModel::class.java)
    }

    fun startUpdates(observer: (RepositoriesUiState) -> Unit) =
        observable.register(observer)

    fun stopUpdates() = observable.unregister()

    fun onItemClicked(item: RepositoryItem) {
        observable.postUiState(RepositoriesUiState.Details)
    }
}