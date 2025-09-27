package ru.n857l.githubrepositories.views.input

import java.io.Serializable

interface InputUiState : Serializable {

    fun update(updateInput: UpdateInput)

    abstract class Abstract(
        private val errorVisible: Boolean,
        private val enabled: Boolean
    ) : InputUiState {

        override fun update(updateInput: UpdateInput) {
            updateInput.update(errorVisible, enabled)
        }
    }

    data class Initial(private val userInputText: String) : Abstract(false, true) {

        override fun update(updateInput: UpdateInput) {
            super.update(updateInput)
            updateInput.update(userInputText)
        }
    }

    object NotEnabled : Abstract(false, false)

    object Correct : Abstract(false, true)

    object Incorrect : Abstract(true, true)
}