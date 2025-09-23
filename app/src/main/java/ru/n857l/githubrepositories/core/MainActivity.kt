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
import ru.n857l.githubrepositories.load.presentation.LoadScreen
import ru.n857l.githubrepositories.load.presentation.NavigateToLoad


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        androidx.core.view.WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.fragment_authentication)

        val rootView = findViewById<View>(R.id.authenticationContainer)
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
    }
}

interface Navigate : NavigateToAuthentication, NavigateToLoad {

    fun navigate(screen: Screen)

    override fun navigateToAuthentication() = navigate(AuthenticationScreen)

    override fun navigateToLoad() = navigate(LoadScreen)
}