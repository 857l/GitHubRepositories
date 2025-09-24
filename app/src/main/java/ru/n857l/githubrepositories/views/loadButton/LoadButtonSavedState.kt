package ru.n857l.githubrepositories.views.loadButton

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class LoadButtonSavedState : View.BaseSavedState {

    private lateinit var state : LoadButtonUiState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn){
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(LoadButtonUiState::class.java.classLoader, LoadButtonUiState::class.java) as LoadButtonUiState
        }
        else {
            parcelIn.readSerializable() as LoadButtonUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): LoadButtonUiState = state

    fun save(uiState: LoadButtonUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<LoadButtonSavedState> {
        override fun createFromParcel(parcel: Parcel): LoadButtonSavedState =
            LoadButtonSavedState(parcel)

        override fun newArray(size: Int): Array<LoadButtonSavedState?> =
            arrayOfNulls(size)
    }
}