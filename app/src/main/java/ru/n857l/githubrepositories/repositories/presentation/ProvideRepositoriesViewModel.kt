package ru.n857l.githubrepositories.repositories.presentation

import ru.n857l.githubrepositories.core.Core
import ru.n857l.githubrepositories.core.Module
import ru.n857l.githubrepositories.di.AbstractProvideViewModel
import ru.n857l.githubrepositories.di.ProvideViewModel

class ProvideRepositoriesViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, RepositoriesViewModel::class.java) {

    override fun module() = RepositoriesModule(core)
}

class RepositoriesModule(
    private val core: Core
) : Module<RepositoriesViewModel> {

    override fun viewModel() =
        RepositoriesViewModel(RepositoriesRepository.Base(), core.clearViewModel)
}