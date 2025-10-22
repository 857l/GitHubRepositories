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
                clearViewModel.clear(RepositoriesViewModel::class.java)
                RepositoriesUiState.EmptyRepositories
            } else {
                RepositoriesUiState.Show(repositoriesRepository)
            }
        } else {
            RepositoriesUiState.Empty
        }
    }

    fun repositoriesList() = repositoriesRepository.data()

    override fun clear() {
        clearViewModel.clear(RepositoriesViewModel::class.java)
    }
}