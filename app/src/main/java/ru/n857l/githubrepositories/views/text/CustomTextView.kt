package ru.n857l.githubrepositories.views.text

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.n857l.githubrepositories.views.UpdateUiState

class CustomTextView : AppCompatTextView, UpdateTextView {

    private var state: TextUiState = TextUiState.Empty

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = TextViewSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as TextViewSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: TextUiState) {
        state = uiState
        state.update(this)
    }

    override fun update(text: String) {
        this.text = text
    }
}

interface UpdateTextView : UpdateUiState<TextUiState> {
    fun update(text: String)
}