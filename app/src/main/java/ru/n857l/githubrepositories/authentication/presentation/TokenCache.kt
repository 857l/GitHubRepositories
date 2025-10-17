package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.core.PreferencesProvider
import ru.n857l.githubrepositories.core.SaveRead

interface TokenCache : SaveRead<String> {

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

        private companion object {
            const val TOKEN_FILENAME = "tokenFile"
            const val TOKEN = "token"
        }
    }
}