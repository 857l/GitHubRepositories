package ru.n857l.githubrepositories.di

import ru.n857l.githubrepositories.core.Core
import ru.n857l.githubrepositories.core.Module

abstract class AbstractProvideViewModel(
    protected val core: Core,
    private val nextChain: ProvideViewModel,
    private val viewModelClass: Class<out MyViewModel>
) : ProvideViewModel {

    override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T {
        return if (clasz == viewModelClass)
            module().viewModel() as T
        else
            nextChain.makeViewModel(clasz)
    }

    protected abstract fun module(): Module<*>
}

interface MyViewModel {
    fun clear()
}