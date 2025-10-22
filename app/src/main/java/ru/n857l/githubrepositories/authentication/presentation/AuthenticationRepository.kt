package ru.n857l.githubrepositories.authentication.presentation

import android.util.Log
import ru.n857l.githubrepositories.authentication.presentation.data.LoadResult
import ru.n857l.githubrepositories.cloud_datasource.GitHubApiService
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesCache

interface AuthenticationRepository {

    fun clear()

    fun token(): String

    fun saveUserInput(value: String)

    fun tokenIsValid(text: String): Boolean

    suspend fun load(): LoadResult

    class Base(
        private val token: TokenCache,
        private val repositoriesCache: RepositoriesCache,
        private val service: GitHubApiService
    ) : AuthenticationRepository {

        override fun token(): String {
            return token.read()
        }

        override fun saveUserInput(value: String) {
            token.save(value)
        }

        override fun clear() {
            token.clear()
        }

        override fun tokenIsValid(text: String): Boolean {
            val regex = Regex("^[A-Za-z0-9_]+$")
            return regex.matches(text)
        }

        override suspend fun load(): LoadResult {
            try {
                val tokenHeader = "Bearer ${token.read()}"
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