package ru.n857l.githubrepositories.dialog.di

import ru.n857l.githubrepositories.core.di.Core
import ru.n857l.githubrepositories.core.di.Module
import ru.n857l.githubrepositories.di.AbstractProvideViewModel
import ru.n857l.githubrepositories.di.ProvideViewModel
import ru.n857l.githubrepositories.dialog.presentation.ErrorDialogViewModel

class ProvideErrorDialogViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, ErrorDialogViewModel::class.java) {

    override fun module() = ErrorDialogViewModule(core)
}

class ErrorDialogViewModule(
    private val core: Core
) : Module<ErrorDialogViewModel> {

    override fun viewModel(): ErrorDialogViewModel {

        return ErrorDialogViewModel(
            core.errorCache,
            core.clearViewModel
        )
    }
}