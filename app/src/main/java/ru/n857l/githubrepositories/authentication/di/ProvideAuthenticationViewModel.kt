package ru.n857l.githubrepositories.authentication.presentation.di

import ru.n857l.githubrepositories.authentication.presentation.AuthenticationRepository
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationUiState
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationViewModel
import ru.n857l.githubrepositories.core.RunAsync
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.core.di.Core
import ru.n857l.githubrepositories.core.di.Module
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

    override fun viewModel(): AuthenticationViewModel {



        return AuthenticationViewModel(
            repository = AuthenticationRepository.Base(
                tokenCache = core.tokenCache,
                dao = core.repositoriesCacheModule.dao(),
                service = core.service
            ),
            clearViewModel = core.clearViewModel,
            observable = UiObservable.Base<AuthenticationUiState>(),
            runAsync = RunAsync.Base()
        )
    }
}