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
import androidx.recyclerview.widget.LinearLayoutManager
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.authentication.presentation.NavigateToAuthentication
import ru.n857l.githubrepositories.core.AbstractFragment
import ru.n857l.githubrepositories.databinding.FragmentRepositoriesBinding

class RepositoriesFragment : AbstractFragment<FragmentRepositoriesBinding>(), MenuProvider {

    private val itemsAdapter = RepositoriesItemAdapter()

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRepositoriesBinding =
        FragmentRepositoriesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        val list = ArrayList<RepositoryItemUiState>()
        list.add(RepositoryItemUiState("moko-web3", "Kotlin", "Ethereum Web3 implementation"))
        list.add(
            RepositoryItemUiState(
                "moko-web3",
                "Kotlin",
                "Template project of a Mobile (Android & iOS) Kotlin MultiPlatform project with the MOKO libraries and modularized architecture"
            )
        )

        itemsAdapter.update(list)
        binding.repositoriesList.adapter = itemsAdapter

        binding.repositoriesList.addItemDecoration(addDivider())
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.fragment_repositories, menu)
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
}

//TODO BackStackNavigation