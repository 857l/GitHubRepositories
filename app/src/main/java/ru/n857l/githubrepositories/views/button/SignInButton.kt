package ru.n857l.githubrepositories.views.button

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class SignInButton : AppCompatButton, UpdateSignInButton {

    private lateinit var state : SignInUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = SignInSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as SignInSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: SignInUiState) {
        state = uiState
        state.update(this)
    }

    override fun update(visibility: Int) {
        this.visibility = visibility
    }
}

interface UpdateSignInButton {

    fun update(uiState: SignInUiState)

    fun update(visibility: Int)
}