package ru.n857l.githubrepositories.core.di

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.n857l.githubrepositories.di.MyViewModel

abstract class AbstractFragment<B : ViewBinding, V : MyViewModel> : Fragment() {

    protected lateinit var viewModel: V

    protected var _binding: B? = null
    protected val binding: B
        get() = _binding ?: throw RuntimeException("Binding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(inflater, container)
        return binding.root
    }

    protected abstract fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): B

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}