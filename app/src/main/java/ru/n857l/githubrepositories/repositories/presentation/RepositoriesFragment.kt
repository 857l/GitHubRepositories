package ru.n857l.githubrepositories.repositories.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.DividerItemDecoration
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.authentication.presentation.NavigateToAuthentication
import ru.n857l.githubrepositories.core.AbstractFragment
import ru.n857l.githubrepositories.core.App
import ru.n857l.githubrepositories.databinding.FragmentRepositoriesBinding
import ru.n857l.githubrepositories.errorrepositories.presentation.NavigateToErrorRepositories

class RepositoriesFragment : AbstractFragment<FragmentRepositoriesBinding>(), MenuProvider {

    private lateinit var viewModel: RepositoriesViewModel

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRepositoriesBinding =
        FragmentRepositoriesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = (requireActivity().application as App).repositoriesViewModel

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        setAdapter(RepositoriesItemAdapter())

        binding.repositoriesList.addItemDecoration(addDivider())

        val uiState = viewModel.init(savedInstanceState == null)

        uiState.navigate(requireActivity() as NavigateToErrorRepositories)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.logout_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.action_logout) (requireActivity() as NavigateToAuthentication).navigateToAuthentication()
        return true
    }

    private fun addDivider(): DividerItemDecoration {
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
            divider.setDrawable(it)
        }
        return divider
    }

    fun setAdapter(adapter: RepositoriesItemAdapter) {
        adapter.update(viewModel.repositoriesList())
        binding.repositoriesList.adapter = adapter
    }
}

//TODO BackStackNavigation