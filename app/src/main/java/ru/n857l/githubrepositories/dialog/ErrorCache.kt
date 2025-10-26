package ru.n857l.githubrepositories.dialog

import ru.n857l.githubrepositories.core.di.Mutable
import ru.n857l.githubrepositories.core.di.PreferencesProvider

interface ErrorCache : Mutable<String> {

    class Base(
        preferencesProvider: PreferencesProvider
    ) : ErrorCache {

        private val sharedPreferences =
            preferencesProvider.provideSharedPreferences(ERROR_CACHE_FILENAME)

        override fun read(): String =
            sharedPreferences.getString(ERROR, "") ?: ""

        override fun save(data: String) {
            sharedPreferences.edit()
                .putString(ERROR, data)
                .apply()
        }

        override fun clear() {
            sharedPreferences.edit()
                .putString(ERROR, "")
                .apply()
        }

        private companion object {
            const val ERROR_CACHE_FILENAME = "errorCacheFile"
            const val ERROR = "error"
        }
    }
}