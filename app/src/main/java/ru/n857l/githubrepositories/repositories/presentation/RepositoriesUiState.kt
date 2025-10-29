package ru.n857l.githubrepositories.repositories.presentation

import ru.n857l.githubrepositories.core.NavigateToFrame
import ru.n857l.githubrepositories.details.NavigateToDetails
import ru.n857l.githubrepositories.errorrepositories.presentation.NavigateToErrorRepositories
import java.io.Serializable

interface RepositoriesUiState : Serializable {

    fun update() = Unit

    fun navigate(navigate: NavigateToFrame) = Unit

    object Empty : RepositoriesUiState

    object EmptyRepositories : RepositoriesUiState {
        override fun navigate(navigate: NavigateToFrame) {
            (navigate as NavigateToErrorRepositories).navigateToErrorRepositories()
        }
    }

    object Details : RepositoriesUiState {
        override fun navigate(navigate: NavigateToFrame) {
            (navigate as NavigateToDetails).navigateToDetails()
        }
    }
}