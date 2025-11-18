package ru.n857l.githubrepositories.core.di

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.viewbinding.ViewBinding
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationScreen
import ru.n857l.githubrepositories.core.Navigate
import ru.n857l.githubrepositories.di.MyViewModel

abstract class AbstractFragmentWithMenu<B : ViewBinding, V : MyViewModel> :
    AbstractFragment<B, V>(), MenuProvider {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.action_logout) (requireActivity() as Navigate).navigateClearingStack(
            AuthenticationScreen
        )
        viewModel.clear()
        return true
    }
}