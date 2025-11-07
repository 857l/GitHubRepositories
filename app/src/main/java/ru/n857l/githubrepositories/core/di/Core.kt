package ru.n857l.githubrepositories.core.di

import android.content.Context
import ru.n857l.githubrepositories.core.cache.ErrorCache
import ru.n857l.githubrepositories.core.cache.TokenCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesCacheModule
import ru.n857l.githubrepositories.details.NumberDetails

class Core(context: Context, var clearViewModel: ClearViewModel) {

    var resourceProvider = ResourceProvider.Base(context)
    var repositoriesCacheModule = RepositoriesCacheModule.Base(context)
    var tokenCache = TokenCache.Base(resourceProvider)
    var errorCache = ErrorCache.Base(resourceProvider)
    var numberDetails = NumberDetails.Base()
}