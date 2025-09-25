package ru.n857l.githubrepositories.views.loadButton

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import ru.n857l.githubrepositories.views.UpdateUiState

class LoadButton : AppCompatButton, UpdateLoadButton {

    private lateinit var state: LoadButtonUiState

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = LoadButtonSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as LoadButtonSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(enable: Boolean, visibility: Int) {
        this.visibility = visibility
    }

    override fun update(state: LoadButtonUiState) {
        this.state = state
        this.state.update(this)
    }
}

interface UpdateLoadButton : UpdateUiState<LoadButtonUiState> {
    fun update(enable: Boolean, visibility: Int)
}