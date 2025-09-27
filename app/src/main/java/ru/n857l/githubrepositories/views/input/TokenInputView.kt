package ru.n857l.githubrepositories.views.input

import android.content.Context
import android.os.Parcelable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.databinding.InputBinding
import ru.n857l.githubrepositories.views.UpdateUiState

class TokenInputView : FrameLayout, UpdateInput {

    private var state: InputUiState = InputUiState.Initial("")
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
            inputLayout.error = inputLayout.context.getString(R.string.incorrect_message)
        inputLayout.isEnabled = enabled
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        binding.tokenInputEditText.addTextChangedListener(textWatcher)
    }

    fun removeTextChangedListener(textWatcher: TextWatcher) {
        binding.tokenInputEditText.removeTextChangedListener(textWatcher)
    }

    fun text(): String = binding.tokenInputEditText.text.toString()
}

interface UpdateInput : UpdateUiState<InputUiState> {

    fun update(userInput: String)

    fun update(errorVisible: Boolean, enabled: Boolean)
}