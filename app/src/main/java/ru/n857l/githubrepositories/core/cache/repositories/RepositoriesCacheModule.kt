package ru.n857l.githubrepositories.core.cache.repositories

import android.content.Context
import androidx.room.Room
import ru.n857l.githubrepositories.R

interface RepositoriesCacheModule {

    fun dao(): RepositoriesDao

    fun clearDatabase(): ClearDatabase

    class Base(applicationContext: Context) : RepositoriesCacheModule {

        private val database by lazy {
            Room.databaseBuilder(
                applicationContext,
                RepositoriesDatabase::class.java,
                applicationContext.getString(R.string.app_name)
            ).build()
        }

        override fun dao() = database.dao()

        override fun clearDatabase() = database
    }
}