package ru.n857l.githubrepositories.views

import java.io.Serializable

interface UpdateUiState<T : Serializable> {
    fun update(uiState: T)
}