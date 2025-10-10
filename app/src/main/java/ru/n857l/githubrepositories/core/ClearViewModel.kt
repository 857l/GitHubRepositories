package ru.n857l.githubrepositories.core

import ru.n857l.githubrepositories.di.MyViewModel

interface ClearViewModel {
    fun clear(viewModelClass: Class<out MyViewModel>)
}