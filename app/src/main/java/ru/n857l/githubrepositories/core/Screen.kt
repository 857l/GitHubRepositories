package ru.n857l.githubrepositories.core

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(containerId: Int, fragmentManager: FragmentManager)

    abstract class Replace(private val fragment: Class<out Fragment>) : Screen {

        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            fragmentManager.beginTransaction()
                .replace(containerId, newFragment())
                .commit()
        }

        protected open fun newFragment(): Fragment = fragment.getDeclaredConstructor().newInstance()
    }

    abstract class Add(private val fragmentClass: Class<out Fragment>) : Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .add(containerId, fragmentClass.getDeclaredConstructor().newInstance())
                .addToBackStack(fragmentClass.name)
                .commit()
        }
    }

    abstract class Dialog(
        private val fragment: Class<out DialogFragment>,
        private val args: Bundle? = null
    ) : Screen {

        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            val dialog = newDialog()
            dialog.arguments = args
            dialog.show(fragmentManager, fragment.simpleName)
        }

        protected open fun newDialog(): DialogFragment =
            fragment.getDeclaredConstructor().newInstance()
    }
}