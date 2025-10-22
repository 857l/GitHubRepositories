package ru.n857l.githubrepositories.core

import android.content.Context
import com.google.gson.Gson
import ru.n857l.githubrepositories.authentication.presentation.TokenCache
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesCache

class Core(context: Context, var clearViewModel: ClearViewModel) {

    val gson = Gson()
    var resourceProvider = ResourceProvider.Base(context)
    var repositoriesCache = RepositoriesCache.Base(
        resourceProvider,
        gson
    )
    var tokenCache = TokenCache.Base(resourceProvider)

}