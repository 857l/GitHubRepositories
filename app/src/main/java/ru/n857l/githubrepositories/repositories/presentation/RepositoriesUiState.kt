package ru.n857l.githubrepositories.repositories.presentation

import ru.n857l.githubrepositories.core.NavigateToFrame
import ru.n857l.githubrepositories.core.adapter.RepositoriesItemAdapter
import ru.n857l.githubrepositories.details.presentation.NavigateToDetails
import ru.n857l.githubrepositories.error.presentation.NavigateToErrorRepositories
import java.io.Serializable

interface RepositoriesUiState : Serializable {

    fun update(adapter: RepositoriesItemAdapter) = Unit

    fun navigate(navigate: NavigateToFrame) = Unit

    object Empty : RepositoriesUiState

    data class ShowList(private val list: List<RepositoryItem>) : RepositoriesUiState {
        override fun update(adapter: RepositoriesItemAdapter) {
            adapter.update(list)
        }
    }

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