package ru.n857l.githubrepositories.core.cache.repositories

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RepositoriesCache::class, TokenEntity::class], version = 4)
abstract class RepositoriesDatabase : RoomDatabase(), ClearDatabase {

    abstract fun dao(): RepositoriesDao

    override suspend fun clear() = clearAllTables()
}

interface ClearDatabase {

    suspend fun clear()
}