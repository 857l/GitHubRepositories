package ru.n857l.githubrepositories.repositories.presentation

class RepositoriesViewModel(
    private val repositoriesRepository: RepositoriesRepository
) {

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

    fun repositoriesList() = repositoriesRepository.data()
}