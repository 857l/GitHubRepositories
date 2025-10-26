package ru.n857l.githubrepositories.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.n857l.githubrepositories.databinding.DialogErrorBinding
import ru.n857l.githubrepositories.di.ProvideViewModel

class ErrorDialogFragment : DialogFragment() {

    lateinit var viewModel: ErrorDialogViewModel

    protected var _binding: DialogErrorBinding? = null
    protected val binding: DialogErrorBinding
        get() = _binding ?: throw RuntimeException("Binding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            (requireActivity().application as ProvideViewModel).makeViewModel(ErrorDialogViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}