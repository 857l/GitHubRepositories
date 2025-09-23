package ru.n857l.githubrepositories.views.edit

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class EditSavedState : View.BaseSavedState {

    private lateinit var state : EditUiState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn){
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(EditUiState::class.java.classLoader, EditUiState::class.java) as EditUiState
        }
        else {
            parcelIn.readSerializable() as EditUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): EditUiState = state

    fun save(uiState: EditUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<EditSavedState> {
        override fun createFromParcel(parcel: Parcel): EditSavedState =
            EditSavedState(parcel)


        override fun newArray(size: Int): Array<EditSavedState?> =
            arrayOfNulls(size)

    }

}