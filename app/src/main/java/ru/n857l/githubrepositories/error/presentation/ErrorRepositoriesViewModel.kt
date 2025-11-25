package ru.n857l.githubrepositories.error.presentation

import ru.n857l.githubrepositories.core.di.ClearViewModel
import ru.n857l.githubrepositories.di.MyViewModel

class ErrorRepositoriesViewModel(
    private val clearViewModel: ClearViewModel
) : MyViewModel {

    override fun clear() {
        clearViewModel.clear(ErrorRepositoriesViewModel::class.java)
    }
}