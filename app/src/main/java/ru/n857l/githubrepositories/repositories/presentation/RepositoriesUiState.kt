package ru.n857l.githubrepositories.repositories.presentation

import java.io.Serializable

interface RepositoriesUiState : Serializable {

    object Initial : RepositoriesUiState

    object Empty : RepositoriesUiState

    object Show : RepositoriesUiState

    object Load : RepositoriesUiState

    object ConnectionError : RepositoriesUiState

    object EmptyRepositories : RepositoriesUiState

    object SomthingError : RepositoriesUiState
}