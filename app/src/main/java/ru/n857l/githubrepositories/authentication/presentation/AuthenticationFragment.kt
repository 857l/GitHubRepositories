package ru.n857l.githubrepositories.authentication.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.n857l.githubrepositories.core.AbstractFragment
import ru.n857l.githubrepositories.databinding.FragmentAuthenticationBinding
import ru.n857l.githubrepositories.di.ProvideViewModel
import ru.n857l.githubrepositories.repositories.presentation.NavigateToRepositories

class AuthenticationFragment :
    AbstractFragment<FragmentAuthenticationBinding, AuthenticationViewModel>() {

    private val update: (AuthenticationUiState) -> Unit = { uiState ->
        uiState.update(
            binding.tokenInputLayout,
            binding.singInButton,
            binding.progressBar
        )
        uiState.navigate((requireActivity() as NavigateToRepositories))
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            viewModel.handleUserInput(s.toString())
        }
    }

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthenticationBinding =
        FragmentAuthenticationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity().application as ProvideViewModel)
            .makeViewModel(AuthenticationViewModel::class.java)

        binding.singInButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        binding.tokenInputLayout.addTextChangedListener(textWatcher)
        viewModel.startUpdates(observer = update)
    }

    override fun onPause() {
        super.onPause()
        binding.tokenInputLayout.removeTextChangedListener(textWatcher)
        viewModel.stopUpdates()
    }
}