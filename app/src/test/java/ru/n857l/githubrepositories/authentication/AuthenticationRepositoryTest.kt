package ru.n857l.githubrepositories.authentication

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationRepository
import ru.n857l.githubrepositories.cloudDatasource.GitHubApiService
import ru.n857l.githubrepositories.cloudDatasource.RepositoryCloud
import ru.n857l.githubrepositories.core.cache.TokenCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesDao
import java.io.IOException

class AuthenticationRepositoryTest {

    //TODO fix tests
    lateinit var repository: AuthenticationRepository
    lateinit var tokenCache: FakeTokenCache
    lateinit var dao: FakeRepositoriesDao
    lateinit var errorCache: FakeErrorCache
    lateinit var service: FakeGitHubApiService

    @Before
    fun setup() {
        tokenCache = FakeTokenCache()
        dao = FakeRepositoriesDao()
        errorCache = FakeErrorCache()
        service = FakeGitHubApiService()
        repository = AuthenticationRepository.Base(
            tokenCache = tokenCache,
            dao = dao,
            service = service
        )
    }

    @Test
    fun case1() = runBlocking {
        repository.saveUserInput("VALID_TOKEN")

        val result = repository.load()

        assertTrue(result.isSuccessful())
        assertTrue(dao.savedList.isNotEmpty())
        assertEquals("", errorCache.read())
    }

    @Test
    fun case2() = runBlocking {
        repository.saveUserInput("INVALID_TOKEN")

        service.throwHttpError(401, "Unauthorized")
        var result = repository.load()

        assertFalse(result.isSuccessful())
        assertEquals("Unauthorized — invalid token", result.message())
        assertEquals("Unauthorized", errorCache.read())
    }

    @Test
    fun case3() = runBlocking {
        repository.saveUserInput("VALID_TOKEN")

        service.throwHttpError(403, "Forbidden")
        val result = repository.load()

        assertFalse(result.isSuccessful())
        assertEquals("Access forbidden — check permissions", result.message())
        assertEquals("Forbidden", errorCache.read())
    }

    @Test
    fun case4() = runBlocking {
        repository.saveUserInput("VALID_TOKEN")

        service.throwNetworkError()
        val result = repository.load()

        assertFalse(result.isSuccessful())
        assertEquals("Please check your internet connection", result.message())
        assertEquals("Please check your internet connection", errorCache.read())
    }

    @Test
    fun case5() = runBlocking {
        repository.saveUserInput("VALID_TOKEN")

        service.throwUnexpectedError("UnexpectedError")
        val result = repository.load()

        assertFalse(result.isSuccessful())
        assertEquals("UnexpectedError", result.message())
        assertEquals("UnexpectedError", errorCache.read())
    }

    class FakeTokenCache : TokenCache {
        private var token: String = ""
        override fun read(): String = token
        override fun save(data: String) {
            token = data
        }

        override fun clear() {
            token = ""
        }
    }

    class FakeRepositoriesDao : RepositoriesDao {
        var savedList = listOf<RepositoriesCache>()
        var cleared = false

        override suspend fun getAll(): List<RepositoriesCache> = savedList

        override suspend fun saveAll(repos: List<RepositoriesCache>) {
            savedList = repos
        }

        override suspend fun clear() {
            cleared = true
            savedList = emptyList()
        }

        override suspend fun getRepositoryByName(repoName: String): RepositoriesCache? {
            TODO("Not yet implemented")
        }
    }

    class FakeGitHubApiService : GitHubApiService {
        private var shouldThrowHttp: Boolean = false
        private var httpCode: Int = 0
        private var httpMessage: String = ""

        private var shouldThrowNetwork: Boolean = false
        private var shouldThrowUnexpected: Boolean = false
        private var unexpectedMessage: String = ""

        fun throwHttpError(code: Int, message: String) {
            shouldThrowHttp = true
            httpCode = code
            httpMessage = message
        }

        fun throwNetworkError() {
            shouldThrowNetwork = true
        }

        fun throwUnexpectedError(message: String) {
            shouldThrowUnexpected = true
            unexpectedMessage = message
        }

        override suspend fun fetchRepositories(
            token: String,
            sort: String,
            amount: Int,
            affiliation: String
        ): List<RepositoryCloud> {
            if (shouldThrowHttp)
                throw retrofit2.HttpException(
                    Response.error<String>(
                        httpCode,
                        httpMessage.toResponseBody("application/json".toMediaTypeOrNull())
                    )
                )

            if (shouldThrowNetwork)
                throw IOException("Network failed")

            if (shouldThrowUnexpected)
                throw RuntimeException(unexpectedMessage)

            // Успешный ответ
            return listOf(
                RepositoryCloud(
                    id = 1,
                    name = "Archive",
                    owner = RepositoryCloud.Owner("John Titor"),
                    htmlUrl = "https://github.com/Daviex/Steins-Gate-Archive-Decompiler",
                    description = "...",
                    language = "SERN",
                    license = RepositoryCloud.License("IBN 5100"),
                    forks = 10,
                    stars = 2,
                    watchers = 34,
                    branch = "master"
                )
            )
        }

        override suspend fun fetchReadme(
            token: String,
            ownerName: String,
            repoName: String
        ): RepositoryCloud.Readme {
            TODO("Not yet implemented")
        }
    }
}