package ru.n857l.githubrepositories.repositories.presentation

class RepositoriesViewModel(
    private val repository: RepositoriesRepository
) {

    fun addRepositoriesItem(repositoryItem: RepositoryItem) {
        val data = repository.repositoriesList()
        data.add(repositoryItem)
        repository.updateRepositoriesList(data)
    }

    fun init(isFirstRun: Boolean = true): RepositoriesUiState {
        return if (isFirstRun) {
            val data = repository.repositoriesList()
            return if (data.isEmpty()) {
                RepositoriesUiState.EmptyRepositories
            } else {
                RepositoriesUiState.Show(repository)
            }
        } else {
            RepositoriesUiState.Empty
        }
    }

    fun repositoriesList() = repository.repositoriesList()
}