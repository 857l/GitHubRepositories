package ru.n857l.githubrepositories.core

import android.content.Context
import android.content.SharedPreferences

interface ResourceProvider : PreferencesProvider {

    class Base(
        private val context: Context
    ) : ResourceProvider {

        override fun provideSharedPreferences(name: String): SharedPreferences =
            context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}

interface PreferencesProvider {

    fun provideSharedPreferences(name: String): SharedPreferences
}