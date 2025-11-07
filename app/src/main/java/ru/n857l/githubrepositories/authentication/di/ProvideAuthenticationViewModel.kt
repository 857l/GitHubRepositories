package ru.n857l.githubrepositories.authentication.presentation.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationRepository
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationUiState
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationViewModel
import ru.n857l.githubrepositories.cloudDatasource.GitHubApiService
import ru.n857l.githubrepositories.core.RunAsync
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.core.di.Core
import ru.n857l.githubrepositories.core.di.Module
import ru.n857l.githubrepositories.di.AbstractProvideViewModel
import ru.n857l.githubrepositories.di.ProvideViewModel
import java.util.concurrent.TimeUnit

class ProvideAuthenticationViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(core, next, AuthenticationViewModel::class.java) {

    override fun module() = AuthenticationModule(core)
}

class AuthenticationModule(
    private val core: Core
) : Module<AuthenticationViewModel> {

    override fun viewModel(): AuthenticationViewModel {

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GitHubApiService::class.java)

        return AuthenticationViewModel(
            repository = AuthenticationRepository.Base(
                tokenCache = core.tokenCache,
                dao = core.repositoriesCacheModule.dao(),
                errorCache = core.errorCache,
                service = service
            ),
            clearViewModel = core.clearViewModel,
            observable = UiObservable.Base<AuthenticationUiState>(),
            runAsync = RunAsync.Base()
        )
    }
}