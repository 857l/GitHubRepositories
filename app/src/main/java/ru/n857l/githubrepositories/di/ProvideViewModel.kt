package ru.n857l.githubrepositories.di

import ru.n857l.githubrepositories.authentication.presentation.di.ProvideAuthenticationViewModel
import ru.n857l.githubrepositories.core.di.Core
import ru.n857l.githubrepositories.details.di.ProvideDetailsViewModel
import ru.n857l.githubrepositories.dialog.di.ProvideErrorDialogViewModel
import ru.n857l.githubrepositories.repositories.presentation.di.ProvideRepositoriesViewModel

interface ProvideViewModel {

    fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T

    class Make(core: Core) : ProvideViewModel {

        private var chain: ProvideViewModel

        init {
            chain = Error()
            chain = ProvideAuthenticationViewModel(core, chain)
            chain = ProvideRepositoriesViewModel(core, chain)
            chain = ProvideErrorDialogViewModel(core, chain)
            chain = ProvideDetailsViewModel(core, chain)
        }

        override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T =
            chain.makeViewModel(clasz)
    }

    class Error : ProvideViewModel {

        override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T {
            throw IllegalStateException("unknown class $clasz")
        }
    }
}