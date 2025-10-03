package ru.n857l.githubrepositories.errorrepositories.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.authentication.presentation.NavigateToAuthentication
import ru.n857l.githubrepositories.core.AbstractFragment
import ru.n857l.githubrepositories.databinding.FragmentErrorRepositoriesBinding

class ErrorRepositoriesFragment : AbstractFragment<FragmentErrorRepositoriesBinding>(),
    MenuProvider {

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentErrorRepositoriesBinding =
        FragmentErrorRepositoriesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
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