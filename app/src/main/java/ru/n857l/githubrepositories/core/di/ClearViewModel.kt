package ru.n857l.githubrepositories.core.di

import ru.n857l.githubrepositories.di.MyViewModel

interface ClearViewModel {
    fun clear(viewModelClass: Class<out MyViewModel>)
}