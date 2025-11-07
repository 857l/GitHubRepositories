package ru.n857l.githubrepositories.views.text

import java.io.Serializable

interface TextUiState : Serializable {

    fun update(
        updateTextView: UpdateTextView
    ) = Unit

    abstract class Abstract(
        private val text: String
    ) : TextUiState {

        override fun update(updateTextView: UpdateTextView) {
            updateTextView.update(text)
        }
    }

    object Empty : TextUiState

    data class Show(private val text: String) : Abstract(text)

}