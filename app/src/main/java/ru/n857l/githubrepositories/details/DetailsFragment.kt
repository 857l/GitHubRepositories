package ru.n857l.githubrepositories.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import ru.n857l.githubrepositories.core.di.AbstractFragmentWithMenu
import ru.n857l.githubrepositories.databinding.FragmentDetailsBinding

class DetailsFragment : AbstractFragmentWithMenu<FragmentDetailsBinding, DetailsViewModel>() {

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding =
        FragmentDetailsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        viewModel = DetailsViewModel()
    }
}