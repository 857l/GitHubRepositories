package ru.n857l.githubrepositories.repositories.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import ru.n857l.githubrepositories.R
import ru.n857l.githubrepositories.core.adapter.LanguageColorProvider
import ru.n857l.githubrepositories.core.adapter.RepositoriesItemAdapter
import ru.n857l.githubrepositories.core.di.AbstractFragmentWithMenu
import ru.n857l.githubrepositories.databinding.FragmentRepositoriesBinding
import ru.n857l.githubrepositories.di.ProvideViewModel
import ru.n857l.githubrepositories.error.presentation.NavigateToErrorRepositories

class RepositoriesFragment :
    AbstractFragmentWithMenu<FragmentRepositoriesBinding, RepositoriesViewModel>() {

    private lateinit var adapter: RepositoriesItemAdapter

    private val update: (RepositoriesUiState) -> Unit = { uiState ->
        uiState.update(adapter)
        uiState.navigate(requireActivity() as NavigateToErrorRepositories)
    }

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRepositoriesBinding =
        FragmentRepositoriesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel =
            (requireActivity().application as ProvideViewModel).makeViewModel(RepositoriesViewModel::class.java)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        super.onViewCreated(view, savedInstanceState)

        adapter = RepositoriesItemAdapter(
            LanguageColorProvider(requireContext()),
            clickListener = { item ->
                viewModel.onItemClicked(item)
            }
        )

        binding.repositoriesList.adapter = adapter
        binding.repositoriesList.addItemDecoration(addDivider())

        viewModel.init()
    }

    private fun addDivider(): DividerItemDecoration {
        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
            divider.setDrawable(it)
        }
        return divider
    }

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates(observer = update)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }
}

//TODO BackStackNavigation