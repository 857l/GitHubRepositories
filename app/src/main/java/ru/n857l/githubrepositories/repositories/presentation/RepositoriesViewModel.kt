package ru.n857l.githubrepositories.repositories.presentation

import ru.n857l.githubrepositories.core.ClearViewModel
import ru.n857l.githubrepositories.di.MyViewModel

class RepositoriesViewModel(
    private val repository: RepositoriesRepository,
    private val clearViewModel: ClearViewModel
) : MyViewModel {

    fun init(isFirstRun: Boolean = true): RepositoriesUiState {
        return if (isFirstRun) {
            val data = repository.data()
            return if (data.isEmpty()) {
                clearViewModel.clear(RepositoriesViewModel::class.java)
                RepositoriesUiState.EmptyRepositories
            } else {
                RepositoriesUiState.Show(repository)
            }
        } else {
            RepositoriesUiState.Empty
        }
    }

    fun repositoriesList() = repository.data()

    override fun clear() {
        clearViewModel.clear(RepositoriesViewModel::class.java)
    }
}