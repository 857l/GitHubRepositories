package ru.n857l.githubrepositories.core

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.viewbinding.ViewBinding
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.authentication.presentation.NavigateToAuthentication

abstract class AbstractFragmentWithMenu<B : ViewBinding> : AbstractFragment<B>(), MenuProvider {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.action_logout) (requireActivity() as NavigateToAuthentication).navigateToAuthentication()
        return true
    }
}