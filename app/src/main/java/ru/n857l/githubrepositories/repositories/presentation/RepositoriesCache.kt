package ru.n857l.githubrepositories.repositories.presentation

import ru.n857l.githubrepositories.core.PreferencesProvider

interface RepositoriesCache {

    fun read(): Triple<String, String, String>
    fun save(data: Triple<String, String, String>)

    class Base(
        preferencesProvider: PreferencesProvider
    ) : RepositoriesCache {

        private val sharedPreferences =
            preferencesProvider.provideSharedPreferences(REPOSITORIES_FILENAME)

        override fun read(): Triple<String, String, String> = Triple(
            sharedPreferences.getString(REPOSITORY_NAME, "") ?: "",
            sharedPreferences.getString(LANGUAGE_NAME, "") ?: "",
            sharedPreferences.getString(REPOSITORY_DESCRIPTION, "") ?: ""
        )

        override fun save(data: Triple<String, String, String>) {
            sharedPreferences.edit()
                .putString(REPOSITORY_NAME, data.first)
                .putString(LANGUAGE_NAME, data.second)
                .putString(REPOSITORY_DESCRIPTION, data.third)
                .apply()
        }
    }

    private companion object {
        const val REPOSITORIES_FILENAME = "repositories"
        const val REPOSITORY_NAME = "repositoryName"
        const val LANGUAGE_NAME = "languageName"
        const val REPOSITORY_DESCRIPTION = "repositoryDescription"
    }
}