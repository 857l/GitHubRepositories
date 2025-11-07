package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.authentication.presentation.data.LoadResult
import ru.n857l.githubrepositories.cloudDatasource.GitHubApiService
import ru.n857l.githubrepositories.core.cache.ErrorCache
import ru.n857l.githubrepositories.core.cache.TokenCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesDao
import java.io.IOException

interface AuthenticationRepository {

    fun clearToken()

    fun token(): String

    fun saveUserInput(value: String)

    fun tokenIsValid(text: String): Boolean

    suspend fun load(): LoadResult

    class Base(
        private val tokenCache: TokenCache,
        private val dao: RepositoriesDao,
        private val errorCache: ErrorCache,
        private val service: GitHubApiService
    ) : AuthenticationRepository {

        override fun token(): String {
            return tokenCache.read()
        }

        override fun saveUserInput(value: String) {
            tokenCache.save(value)
        }

        override fun clearToken() {
            tokenCache.clear()
        }

        override fun tokenIsValid(text: String): Boolean {
            val regex = Regex("^[A-Za-z0-9_-]+$")
            return regex.matches(text)
        }

        override suspend fun load(): LoadResult {
            return try {
                val tokenHeader = "Bearer ${tokenCache.read()}"
                val result = service.fetchRepositories(token = tokenHeader)
                dao.clear()
                val parsedResult: List<RepositoriesCache> =
                    result.map { data ->
                        RepositoriesCache(
                            data.name,
                            data.htmlUrl,
                            data.description,
                            data.language,
                            data.license?.name,
                            data.forks,
                            data.stars,
                            data.watchers
                        )
                    }
                dao.saveAll(parsedResult)

                //Log.d("GitHubApi", "Loaded ${result.size} repositories")
                LoadResult.Success

            } catch (e: retrofit2.HttpException) {
                val code = e.code()
                val errorBody = e.response()?.errorBody()?.string().orEmpty()

                //Log.e("GitHubApi", "HTTP error $code: $errorBody", e)
                errorCache.save(errorBody)
                LoadResult.Error(handleResponseCode(code, errorBody))

            } catch (e: IOException) {
                //Log.e("GitHubApi", "Network error", e)
                errorCache.save("Please check your internet connection")
                LoadResult.Error("Please check your internet connection")

            } catch (e: Exception) {
                //Log.e("GitHubApi", "Unexpected error", e)
                errorCache.save(e.message ?: "Unknown error occurred")
                LoadResult.Error(e.message ?: "Unknown error occurred")
            }
        }

        private fun handleResponseCode(code: Int, errorBody: String): String {
            return when (code) {
                401 -> "Unauthorized — invalid token"
                403 -> "Access forbidden — check permissions"
                404 -> "Endpoint not found"
                in 500..599 -> "Server error ($code): please try again later"
                else -> errorBody.ifBlank { "Unexpected error ($code)" }
            }
        }
    }
}