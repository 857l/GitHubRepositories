package ru.n857l.githubrepositories.core

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationScreen
import ru.n857l.githubrepositories.authentication.presentation.NavigateToAuthentication

import ru.n857l.githubrepositories.repositories.presentation.NavigateToRepositories
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesScreen


class MainActivity : AppCompatActivity(), Navigate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        androidx.core.view.WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_main)

        val rootView = findViewById<View>(R.id.container)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = systemInsets.left,
                top = systemInsets.top,
                right = systemInsets.right,
                bottom = systemInsets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }

        navigateToAuthentication()
    }

    override fun navigate(screen: Screen) = screen.show(R.id.container, supportFragmentManager)
}

interface Navigate : NavigateToAuthentication, NavigateToRepositories {

    fun navigate(screen: Screen)

    override fun navigateToAuthentication() = navigate(AuthenticationScreen)

    override fun navigateToRepositories() = navigate(RepositoriesScreen)
}