package ru.n857l.githubrepositories.views.visibility

interface UpdateVisibility {

    fun update(uiState: VisibilityUiState)

    fun update(visibility: Int)
}