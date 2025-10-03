package ru.n857l.githubrepositories.authentication.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.n857l.githubrepositories.core.AbstractFragment
import ru.n857l.githubrepositories.core.App
import ru.n857l.githubrepositories.databinding.FragmentAuthenticationBinding
import ru.n857l.githubrepositories.repositories.presentation.NavigateToRepositories

class AuthenticationFragment : AbstractFragment<FragmentAuthenticationBinding>() {

    private lateinit var viewModel: AuthenticationViewModel
    private var uiState: AuthenticationUiState = AuthenticationUiState.Empty

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            uiState = viewModel.handleUserInput(s.toString())
            uiState.update(binding.tokenInputLayout, binding.singInButton, binding.progressBar)
        }
    }

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthenticationBinding =
        FragmentAuthenticationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity().application as App).authenticationViewModel

        binding.singInButton.setOnClickListener {
            (requireActivity() as NavigateToRepositories).navigateToRepositories()
        }

        val uiState = viewModel.init(savedInstanceState == null)
        uiState.update(binding.tokenInputLayout, binding.singInButton, binding.progressBar)
    }

    override fun onResume() {
        super.onResume()
        binding.tokenInputLayout.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.tokenInputLayout.removeTextChangedListener(textWatcher)
    }
}