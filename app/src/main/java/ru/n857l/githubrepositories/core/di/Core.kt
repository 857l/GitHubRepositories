package ru.n857l.githubrepositories.core.di

import android.content.Context
import com.google.gson.Gson
import ru.n857l.githubrepositories.core.cache.RepositoriesCache
import ru.n857l.githubrepositories.core.cache.TokenCache
import ru.n857l.githubrepositories.dialog.ErrorCache

class Core(context: Context, var clearViewModel: ClearViewModel) {

    val gson = Gson()
    var resourceProvider = ResourceProvider.Base(context)
    var repositoriesCache = RepositoriesCache.Base(
        resourceProvider,
        gson
    )
    var tokenCache = TokenCache.Base(resourceProvider)
    var errorCache = ErrorCache.Base(resourceProvider)
}