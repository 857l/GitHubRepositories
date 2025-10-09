package ru.n857l.githubrepositories.core

import android.content.Context
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesCache

class Core {

    lateinit var resourceProvider: ResourceProvider
    lateinit var repositoriesCache: RepositoriesCache

    fun init(context: Context) {
        resourceProvider = ResourceProvider.Base(context)
        repositoriesCache = RepositoriesCache.Base(resourceProvider)
    }
}