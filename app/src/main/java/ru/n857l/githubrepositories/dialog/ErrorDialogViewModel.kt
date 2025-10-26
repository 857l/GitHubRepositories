package ru.n857l.githubrepositories.dialog

import ru.n857l.githubrepositories.core.di.ClearViewModel
import ru.n857l.githubrepositories.di.MyViewModel

class ErrorDialogViewModel(
    private val errorCache: ErrorCache,
    private val clearViewModel: ClearViewModel
) : MyViewModel {

    override fun clear() {
        clearViewModel.clear(ErrorDialogViewModel::class.java)
    }

    fun error(): String = errorCache.read()
}