package ru.n857l.githubrepositories.authentication.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.n857l.githubrepositories.core.App
import ru.n857l.githubrepositories.databinding.FragmentAuthenticationBinding
import ru.n857l.githubrepositories.repositories.presentation.NavigateToRepositories

class AuthenticationFragment : Fragment() {

    private var _binding: FragmentAuthenticationBinding? = null

    private val binding
        get() = _binding!!

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

        val viewModel: AuthenticationViewModel =
            (requireActivity().application as App).authenticationViewModel

        binding.singInButton.setOnClickListener {
            (requireActivity() as NavigateToRepositories).navigateToRepositories()
        }

        val uiState = viewModel.init(savedInstanceState == null)
        uiState.update(binding.tokenInputLayout, binding.singInButton, binding.progressBar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}