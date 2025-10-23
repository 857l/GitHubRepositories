package ru.n857l.githubrepositories.authentication.presentation

import android.util.Log
import ru.n857l.githubrepositories.authentication.presentation.data.LoadResult
import ru.n857l.githubrepositories.cloud_datasource.GitHubApiService
import ru.n857l.githubrepositories.core.cache.RepositoriesCache
import ru.n857l.githubrepositories.core.cache.TokenCache

interface AuthenticationRepository {

    fun clear()

    fun token(): String

    fun saveUserInput(value: String)

    fun tokenIsValid(text: String): Boolean

    suspend fun load(): LoadResult

    class Base(
        private val tokenCache: TokenCache,
        private val repositoriesCache: RepositoriesCache,
        private val service: GitHubApiService
    ) : AuthenticationRepository {

        override fun token(): String {
            return tokenCache.read()
        }

        override fun saveUserInput(value: String) {
            tokenCache.save(value)
        }

        override fun clear() {
            tokenCache.clear()
        }

        override fun tokenIsValid(text: String): Boolean {
            val regex = Regex("^[A-Za-z0-9_]+$")
            return regex.matches(text)
        }

        override suspend fun load(): LoadResult {
            try {
                val tokenHeader = "Bearer ${tokenCache.read()}"
                val result = service.fetchRepositories(token = tokenHeader)

                if (result.isEmpty())
                    return LoadResult.Success
                else {
                    repositoriesCache.save(result)
                    Log.d("857ll", result.toString())
                    return LoadResult.Success
                }
            } catch (e: Exception) {
                Log.d("857ll", e.message.toString())
                return LoadResult.Error(e.message.toString())
            }
        }
    }
}