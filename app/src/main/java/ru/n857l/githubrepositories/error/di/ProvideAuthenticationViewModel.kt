package ru.n857l.githubrepositories.error.di

import ru.n857l.githubrepositories.core.di.Core
import ru.n857l.githubrepositories.core.di.Module
import ru.n857l.githubrepositories.di.AbstractProvideViewModel
import ru.n857l.githubrepositories.di.ProvideViewModel
import ru.n857l.githubrepositories.error.presentation.ErrorRepositoriesViewModel

class ProvideErrorRepositoriesViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, ErrorRepositoriesViewModel::class.java) {

    override fun module() = ErrorRepositoriesModule(core)
}

class ErrorRepositoriesModule(
    private val core: Core
) : Module<ErrorRepositoriesViewModel> {

    override fun viewModel(): ErrorRepositoriesViewModel {
        return ErrorRepositoriesViewModel(
            core.clearViewModel
        )
    }
}