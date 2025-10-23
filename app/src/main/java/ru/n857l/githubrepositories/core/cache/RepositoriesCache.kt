package ru.n857l.githubrepositories.core.cache

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.n857l.githubrepositories.cloud_datasource.RepositoryCloud
import ru.n857l.githubrepositories.core.di.Mutable
import ru.n857l.githubrepositories.core.di.PreferencesProvider

interface RepositoriesCache : Mutable<List<RepositoryCloud>> {

    class Base(
        preferencesProvider: PreferencesProvider,
        private val gson: Gson
    ) : RepositoriesCache {

        private val sharedPreferences =
            preferencesProvider.provideSharedPreferences(REPOSITORIES_FILENAME)

        override fun read(): List<RepositoryCloud> {
            val json = sharedPreferences.getString(REPOSITORIES_KEY, null)
            return if (json != null) {
                val type = object : TypeToken<List<RepositoryCloud>>() {}.type
                gson.fromJson(json, type)
            } else {
                emptyList()
            }
        }

        override fun save(data: List<RepositoryCloud>) {
            val json = gson.toJson(data)
            sharedPreferences.edit()
                .putString(REPOSITORIES_KEY, json)
                .apply()
        }

        override fun clear() {
            sharedPreferences.edit().remove(REPOSITORIES_KEY).apply()
        }
    }

    private companion object {
        const val REPOSITORIES_FILENAME = "repositories"
        const val REPOSITORIES_KEY = "repositories_list"
    }
}