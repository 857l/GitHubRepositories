package ru.n857l.githubrepositories.di

import ru.n857l.githubrepositories.authentication.presentation.di.ProvideAuthenticationViewModel
import ru.n857l.githubrepositories.core.Core
import ru.n857l.githubrepositories.repositories.presentation.ProvideRepositoriesViewModel

interface ProvideViewModel {

    fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T

    class Make(core: Core) : ProvideViewModel {

        private var chain: ProvideViewModel

        init {
            chain = Error()
            chain = ProvideAuthenticationViewModel(core, chain)
            chain = ProvideRepositoriesViewModel(core, chain)
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