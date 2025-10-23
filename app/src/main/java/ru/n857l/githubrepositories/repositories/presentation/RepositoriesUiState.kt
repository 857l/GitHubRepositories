package ru.n857l.githubrepositories.repositories.presentation

import ru.n857l.githubrepositories.errorrepositories.presentation.NavigateToErrorRepositories
import java.io.Serializable

interface RepositoriesUiState : Serializable {

    fun update() = Unit

    fun navigate(navigate: NavigateToErrorRepositories) = Unit

    object Empty : RepositoriesUiState

    object EmptyRepositories : RepositoriesUiState {
        override fun navigate(navigate: NavigateToErrorRepositories) =
            navigate.navigateToErrorRepositories()
    }
}