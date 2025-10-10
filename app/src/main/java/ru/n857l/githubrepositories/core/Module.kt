package ru.n857l.githubrepositories.core

import ru.n857l.githubrepositories.di.MyViewModel

interface Module<T : MyViewModel> {

    fun viewModel(): T
}