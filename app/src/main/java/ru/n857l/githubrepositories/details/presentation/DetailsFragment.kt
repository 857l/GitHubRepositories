package ru.n857l.githubrepositories.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import ru.n857l.githubrepositories.core.di.AbstractFragmentWithMenu
import ru.n857l.githubrepositories.databinding.FragmentDetailsBinding
import ru.n857l.githubrepositories.di.ProvideViewModel

class DetailsFragment : AbstractFragmentWithMenu<FragmentDetailsBinding, DetailsViewModel>() {

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(inflater, container, false)

    private val update: (DetailsUiState) -> Unit = { uiState ->
        uiState.update(
            binding.link,
            binding.license,
            binding.stars,
            binding.forks,
            binding.watchers,
            binding.readme
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        viewModel =
            (requireActivity().application as ProvideViewModel).makeViewModel(DetailsViewModel::class.java)

        viewModel.init()
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