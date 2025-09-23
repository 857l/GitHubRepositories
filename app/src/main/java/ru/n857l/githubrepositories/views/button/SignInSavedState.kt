package ru.n857l.githubrepositories.views.button

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class SignInSavedState : View.BaseSavedState {

    private lateinit var state : SignInUiState

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn){
        state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            parcelIn.readSerializable(SignInUiState::class.java.classLoader, SignInUiState::class.java) as SignInUiState
        }
        else {
            parcelIn.readSerializable() as SignInUiState
        }
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeSerializable(state)
    }

    fun restore(): SignInUiState = state

    fun save(uiState: SignInUiState) {
        state = uiState
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<SignInSavedState> {
        override fun createFromParcel(parcel: Parcel): SignInSavedState =
            SignInSavedState(parcel)


        override fun newArray(size: Int): Array<SignInSavedState?> =
            arrayOfNulls(size)

    }

}