package ru.n857l.githubrepositories.views.button

import java.io.Serializable

interface SignInUiState : Serializable {

    abstract class Abstract(

    ) : SignInUiState{

    }

    object Progress

    object Success

    object Error
}