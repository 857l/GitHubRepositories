package ru.n857l.githubrepositories.error.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import ru.n857l.githubrepositories.core.di.AbstractFragmentWithMenu
import ru.n857l.githubrepositories.databinding.FragmentErrorRepositoriesBinding
import ru.n857l.githubrepositories.di.ProvideViewModel

class ErrorRepositoriesFragment :
    AbstractFragmentWithMenu<FragmentErrorRepositoriesBinding, ErrorRepositoriesViewModel>() {

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentErrorRepositoriesBinding {
        return FragmentErrorRepositoriesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        super.onViewCreated(view, savedInstanceState)
        viewModel = (requireActivity().application as ProvideViewModel)
            .makeViewModel(ErrorRepositoriesViewModel::class.java)
    }
}