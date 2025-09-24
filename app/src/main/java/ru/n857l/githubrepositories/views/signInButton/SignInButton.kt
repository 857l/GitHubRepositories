package ru.n857l.githubrepositories.views.signInButton

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import ru.n857l.githubrepositories.views.visibility.UpdateVisibility
import ru.n857l.githubrepositories.views.visibility.VisibilitySavedState
import ru.n857l.githubrepositories.views.visibility.VisibilityUiState

class SignInButton : AppCompatButton, UpdateVisibility {

    private lateinit var state: VisibilityUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = VisibilitySavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as VisibilitySavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: VisibilityUiState) {
        state = uiState
        state.update(this)
    }

    override fun update(visibility: Int) {
        this.visibility = visibility
    }
}