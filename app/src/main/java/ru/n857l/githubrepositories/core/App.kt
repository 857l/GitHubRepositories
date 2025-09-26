package ru.n857l.githubrepositories.core

import android.app.Application
import android.content.Context
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationRepository
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationViewModel
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesViewModel

class App : Application() {

    lateinit var authenticationViewModel: AuthenticationViewModel
    lateinit var repositoriesViewModel: RepositoriesViewModel

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("GHRAppData", Context.MODE_PRIVATE)

        authenticationViewModel = AuthenticationViewModel(
            AuthenticationRepository.Base(StringCache.Base(sharedPreferences, "token", ""))
        )
        repositoriesViewModel = RepositoriesViewModel(

        )
    }
}