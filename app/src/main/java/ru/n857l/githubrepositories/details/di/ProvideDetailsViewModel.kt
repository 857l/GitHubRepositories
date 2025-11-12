package ru.n857l.githubrepositories.details.di

import ru.n857l.githubrepositories.core.RunAsync
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.core.di.Core
import ru.n857l.githubrepositories.core.di.Module
import ru.n857l.githubrepositories.details.presentation.DetailsRepository
import ru.n857l.githubrepositories.details.presentation.DetailsUiState
import ru.n857l.githubrepositories.details.presentation.DetailsViewModel
import ru.n857l.githubrepositories.di.AbstractProvideViewModel
import ru.n857l.githubrepositories.di.ProvideViewModel


class ProvideDetailsViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, DetailsViewModel::class.java) {

    override fun module() = DetailsModule(core)
}

class DetailsModule(
    private val core: Core
) : Module<DetailsViewModel> {

    override fun viewModel() =
        DetailsViewModel(
            repository = DetailsRepository.Base(
                tokenCache = core.tokenCache,
                dao = core.repositoriesCacheModule.dao(),
                service = core.service
            ),
            clearViewModel = core.clearViewModel,
            observable = UiObservable.Base<DetailsUiState>(),
            runAsync = RunAsync.Base(),
            numberDetails = core.numberDetails
        )
}