package ru.n857l.githubrepositories.views.visibility

import android.view.View
import ru.n857l.githubrepositories.views.UpdateUiState
import java.io.Serializable

interface VisibilityUiState : Serializable {

    fun update(updateVisibility: UpdateVisibility)

    abstract class Abstract(
        private val visible: Int
    ) : VisibilityUiState {

        override fun update(updateVisibility: UpdateVisibility) {
            updateVisibility.update(visible)
        }
    }

    object Visible : Abstract(View.VISIBLE)

    object Gone : Abstract(View.GONE)
}

interface UpdateVisibility : UpdateUiState<VisibilityUiState> {
    fun update(visible: Int)
}