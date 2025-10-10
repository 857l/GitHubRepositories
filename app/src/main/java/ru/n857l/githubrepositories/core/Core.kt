package ru.n857l.githubrepositories.core

import android.content.Context
import ru.n857l.githubrepositories.authentication.presentation.TokenCache
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesCache

class Core(context: Context, var clearViewModel: ClearViewModel) {

    var resourceProvider = ResourceProvider.Base(context)
    var repositoriesCache = RepositoriesCache.Base(resourceProvider)
    var tokenCache = TokenCache.Base(resourceProvider)

}