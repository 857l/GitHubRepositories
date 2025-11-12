package ru.n857l.githubrepositories.core.di

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.n857l.githubrepositories.cloudDatasource.GitHubApiService
import ru.n857l.githubrepositories.core.cache.ErrorCache
import ru.n857l.githubrepositories.core.cache.TokenCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesCacheModule
import ru.n857l.githubrepositories.details.presentation.NumberDetails
import java.util.concurrent.TimeUnit

class Core(context: Context, var clearViewModel: ClearViewModel) {

    var resourceProvider = ResourceProvider.Base(context)
    var repositoriesCacheModule = RepositoriesCacheModule.Base(context)
    var tokenCache = TokenCache.Base(resourceProvider)
    var errorCache = ErrorCache.Base(resourceProvider)
    var numberDetails = NumberDetails.Base()

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(GitHubApiService::class.java)
}