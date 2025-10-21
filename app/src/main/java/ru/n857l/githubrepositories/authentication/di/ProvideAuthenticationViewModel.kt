package ru.n857l.githubrepositories.authentication.presentation.di

import ru.n857l.githubrepositories.authentication.presentation.AuthenticationRepository
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationUiState
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationViewModel
import ru.n857l.githubrepositories.cloud_datasource.GitHubApiService
import ru.n857l.githubrepositories.core.Core
import ru.n857l.githubrepositories.core.Module
import ru.n857l.githubrepositories.core.RunAsync
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.di.AbstractProvideViewModel
import ru.n857l.githubrepositories.di.ProvideViewModel

class ProvideAuthenticationViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, AuthenticationViewModel::class.java) {

    override fun module() = AuthenticationModule(core)
}

class AuthenticationModule(
    private val core: Core
) : Module<AuthenticationViewModel> {

    override fun viewModel() =
        AuthenticationViewModel(
            repository = AuthenticationRepository.Base(
                core.tokenCache,
                GitHubApiService.Base()
            ),
            clearViewModel = core.clearViewModel,
            observable = UiObservable.Base<AuthenticationUiState>(),
            runAsync = RunAsync.Base()
        )
}