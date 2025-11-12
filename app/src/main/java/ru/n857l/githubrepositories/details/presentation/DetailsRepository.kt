package ru.n857l.githubrepositories.details.presentation

import android.util.Base64
import ru.n857l.githubrepositories.authentication.presentation.data.LoadResult
import ru.n857l.githubrepositories.cloudDatasource.GitHubApiService
import ru.n857l.githubrepositories.core.cache.TokenCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesDao
import java.io.IOException

interface DetailsRepository {

    suspend fun data(name: String): RepositoriesCache?

    suspend fun load(repoName: String): LoadResult

    suspend fun readme(repoName: String): String

    class Base(
        private val tokenCache: TokenCache,
        private val dao: RepositoriesDao,
        private val service: GitHubApiService
    ) : DetailsRepository {
        override suspend fun data(repoName: String): RepositoriesCache? =
            dao.getRepositoryByName(repoName)

        override suspend fun load(repoName: String): LoadResult {
            return try {
                val tokenHeader = "Bearer ${tokenCache.read()}"
                val owner = dao.getOwnerByName(repoName)
                val result = service.fetchReadme(
                    token = tokenHeader,
                    ownerName = owner!!,
                    repoName = repoName
                ).content
                val decodedResult = String(Base64.decode(result, Base64.DEFAULT))
                dao.updateReadme(repoName, decodedResult)
                LoadResult.Success
            } catch (e: retrofit2.HttpException) {
                val code = e.code()
                val errorBody = e.response()?.errorBody()?.string().orEmpty()
                LoadResult.Error(handleResponseCode(code, errorBody))

            } catch (e: IOException) {
                LoadResult.Error("Please check your internet connection")

            } catch (e: Exception) {
                LoadResult.Error(e.message ?: "Unknown error occurred")
            }
        }

        override suspend fun readme(repoName: String): String =
            dao.getReadmeByName(repoName)


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