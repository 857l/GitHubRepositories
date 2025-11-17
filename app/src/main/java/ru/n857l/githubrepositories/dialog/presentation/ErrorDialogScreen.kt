package ru.n857l.githubrepositories.dialog.presentation

import android.os.Bundle
import ru.n857l.githubrepositories.core.Screen

object ErrorDialogScreen {

    fun make(message: String): Screen.Dialog {
        val bundle = Bundle().apply {
            putString("error_message", message)
        }

        return object : Screen.Dialog(ErrorDialogFragment::class.java, bundle) {}
    }
}