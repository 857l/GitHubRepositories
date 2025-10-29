package ru.n857l.githubrepositories.repositories.presentation

import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.core.di.ClearViewModel
import ru.n857l.githubrepositories.di.MyViewModel

class RepositoriesViewModel(
    private val repository: RepositoriesRepository,
    private val clearViewModel: ClearViewModel,
    private val observable: UiObservable<RepositoriesUiState>
) : MyViewModel {

    fun init(isFirstRun: Boolean = true) {
        observable.postUiState(
            if (isFirstRun) {
                val data = repository.data()
                if (data.isEmpty()) {
                    clearViewModel.clear(RepositoriesViewModel::class.java)
                    RepositoriesUiState.EmptyRepositories
                } else
                    RepositoriesUiState.Empty
            } else
                RepositoriesUiState.Empty
        )
    }

    fun repositoriesList() = repository.data()

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