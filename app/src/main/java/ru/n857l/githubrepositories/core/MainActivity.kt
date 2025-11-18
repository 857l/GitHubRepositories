package ru.n857l.githubrepositories.core

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentManager
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationScreen
import ru.n857l.githubrepositories.authentication.presentation.NavigateToAuthentication
import ru.n857l.githubrepositories.details.presentation.DetailsScreen
import ru.n857l.githubrepositories.details.presentation.NavigateToDetails
import ru.n857l.githubrepositories.dialog.presentation.ErrorDialogScreen
import ru.n857l.githubrepositories.errorrepositories.presentation.ErrorRepositoriesScreen
import ru.n857l.githubrepositories.errorrepositories.presentation.NavigateToErrorRepositories

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

        if (savedInstanceState == null)
            navigateToAuthentication()
    }

    override fun navigate(screen: Screen) = screen.show(R.id.container, supportFragmentManager)

    override fun showErrorDialog(message: String) {
        navigate(ErrorDialogScreen.make(message))
    }

    override fun navigateClearingStack(screen: Screen) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        navigate(screen)
    }
}

interface Navigate : NavigateToAuthentication, NavigateToRepositories, NavigateToErrorRepositories,
    NavigateToDetails, NavigateToErrorDialog, NavigateClearingStack {

    fun navigate(screen: Screen)

    override fun navigateToAuthentication() = navigate(AuthenticationScreen)

    override fun navigateToRepositories() = navigate(RepositoriesScreen)

    override fun navigateToErrorRepositories() = navigate(ErrorRepositoriesScreen)

    override fun navigateToDetails() = navigate(DetailsScreen)
}

interface NavigateToErrorDialog {
    fun showErrorDialog(message: String)
}

interface NavigateClearingStack {
    fun navigateClearingStack(screen: Screen)
}