package ru.n857l.githubrepositories.authentication.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.n857l.githubrepositories.core.App
import ru.n857l.githubrepositories.databinding.FragmentAuthenticationBinding
import ru.n857l.githubrepositories.repositories.presentation.NavigateToRepositories

class AuthenticationFragment : Fragment() {

    private var _binding: FragmentAuthenticationBinding? = null
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var uiState: AuthenticationUiState

    private val binding
        get() = _binding!!

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            uiState = viewModel.handleUserInput(s.toString())
            uiState.update(binding.tokenInputLayout, binding.singInButton, binding.progressBar)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}