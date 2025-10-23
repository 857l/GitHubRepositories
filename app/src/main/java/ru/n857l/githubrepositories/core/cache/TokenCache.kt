package ru.n857l.githubrepositories.core.cache

import ru.n857l.githubrepositories.core.Mutable
import ru.n857l.githubrepositories.core.PreferencesProvider

interface TokenCache : Mutable<String> {

    class Base(
        preferencesProvider: PreferencesProvider
    ) : TokenCache {

        private val sharedPreferences =
            preferencesProvider.provideSharedPreferences(TOKEN_FILENAME)

        override fun read(): String =
            sharedPreferences.getString(TOKEN, "") ?: ""

        override fun save(data: String) {
            sharedPreferences.edit()
                .putString(TOKEN, data)
                .apply()
        }

        override fun clear() {
            sharedPreferences.edit()
                .putString(TOKEN, "")
                .apply()
        }

        private companion object {
            const val TOKEN_FILENAME = "tokenFile"
            const val TOKEN = "token"
        }
    }
}