package ru.n857l.githubrepositories.repositories.presentation

class RepositoriesViewModel(
    private val repositoriesRepository: RepositoriesRepository.Base
) {

    fun initial(isFirstRun: Boolean = true): RepositoriesUiState {
        if (isFirstRun) {
            return RepositoriesUiState.Show
        } else {
            return RepositoriesUiState.Empty
        }
    }

    fun repositoriesList() = repositoriesRepository.repositoriesList()
}