package ru.n857l.githubrepositories.core

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(containerId: Int, fragmentManager: FragmentManager)

    abstract class Replace(private val fragment: Class<out Fragment>) : Screen {

        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .replace(containerId, newFragment())
                .commit()
        }

        protected open fun newFragment(): Fragment = fragment.getDeclaredConstructor().newInstance()
    }

    abstract class Dialog(private val fragment: Class<out DialogFragment>) : Screen {

        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            newDialog().show(fragmentManager, fragment.simpleName)
        }

        protected open fun newDialog(): DialogFragment =
            fragment.getDeclaredConstructor().newInstance()
    }
}