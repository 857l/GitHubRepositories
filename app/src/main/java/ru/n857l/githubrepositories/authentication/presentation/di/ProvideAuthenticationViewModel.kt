package ru.n857l.githubrepositories.authentication.presentation.di

import ru.n857l.githubrepositories.authentication.presentation.AuthenticationRepository
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationViewModel
import ru.n857l.githubrepositories.core.Core
import ru.n857l.githubrepositories.core.Module
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
        AuthenticationViewModel(AuthenticationRepository.Base(core.tokenCache), core.clearViewModel)
}