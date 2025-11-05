package ru.n857l.githubrepositories.repositories.presentation.di

import ru.n857l.githubrepositories.core.RunAsync
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.core.di.Core
import ru.n857l.githubrepositories.core.di.Module
import ru.n857l.githubrepositories.di.AbstractProvideViewModel
import ru.n857l.githubrepositories.di.ProvideViewModel
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesRepository
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesUiState
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesViewModel

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
        RepositoriesViewModel(
            repository = RepositoriesRepository.Base(
                dao = core.repositoriesCacheModule.dao(),
            ),
            clearViewModel = core.clearViewModel,
            observable = UiObservable.Base<RepositoriesUiState>(),
            runAsync = RunAsync.Base()
        )
}