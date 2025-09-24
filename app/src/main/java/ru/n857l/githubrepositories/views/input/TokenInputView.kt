package ru.n857l.githubrepositories.views.input

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ru.n857l.githubrepositories.databinding.InputBinding

class TokenInputView : FrameLayout, UpdateInput {

    private lateinit var state: InputUiState
    private val binding = InputBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val savedState = InputViewSavedState(it)
            savedState.save(state)
            return savedState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoredState = state as InputViewSavedState
        super.onRestoreInstanceState(restoredState.superState)
        update(restoredState.restore())
    }

    override fun update(uiState: InputUiState) {
        state = uiState
        state.update(this)
    }

    override fun update(userInput: String) {
        binding.tokenInputEditText.setText(userInput)
    }

    override fun update(errorVisible: Boolean, enabled: Boolean) = with(binding) {
        inputLayout.isErrorEnabled = errorVisible
        if (errorVisible)
            inputLayout.error = "Invalid token"
        inputLayout.isEnabled = enabled
    }
}

interface UpdateInput {

    fun update(uiState: InputUiState)

    fun update(userInput: String)

    fun update(errorVisible: Boolean, enabled: Boolean)
}