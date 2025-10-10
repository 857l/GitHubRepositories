package ru.n857l.githubrepositories.repositories.presentation

import ru.n857l.githubrepositories.core.ClearViewModel
import ru.n857l.githubrepositories.di.MyViewModel

class RepositoriesViewModel(
    private val repositoriesRepository: RepositoriesRepository,
    private val clearViewModel: ClearViewModel
) : MyViewModel {

    fun init(isFirstRun: Boolean = true): RepositoriesUiState {
        return if (isFirstRun) {
            val data = repositoriesRepository.data()
            return if (data.isEmpty()) {
                RepositoriesUiState.EmptyRepositories
            } else {
                RepositoriesUiState.Show(repositoriesRepository)
            }
        } else {
            RepositoriesUiState.Empty
        }
    }

    fun clear() {
        clearViewModel.clear(RepositoriesViewModel::class.java)
    }

    fun repositoriesList() = repositoriesRepository.data()
}