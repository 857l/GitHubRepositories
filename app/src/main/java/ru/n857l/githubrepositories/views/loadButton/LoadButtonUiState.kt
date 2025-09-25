package ru.n857l.githubrepositories.views.loadButton

import android.view.View
import java.io.Serializable

interface LoadButtonUiState : Serializable {

    fun update(updateLoadButton: UpdateLoadButton)

    abstract class Abstract(
        private val enable: Boolean,
        private val visibility: Int
    ) : LoadButtonUiState {

        override fun update(updateLoadButton: UpdateLoadButton){
            updateLoadButton.update(enable, visibility)
        }
    }

    object Visible : Abstract(true, View.VISIBLE)

    object NotEnabled : Abstract(false, View.VISIBLE)

    object Gone : Abstract(false, View.GONE)
}