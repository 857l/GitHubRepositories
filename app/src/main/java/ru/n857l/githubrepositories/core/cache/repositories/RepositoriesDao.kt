package ru.n857l.githubrepositories.core.cache.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepositoriesDao {
    @Query("SELECT * FROM repositories_table")
    suspend fun getAll(): List<RepositoriesCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(repos: List<RepositoriesCache>)

    @Query("DELETE FROM repositories_table")
    suspend fun clear()

    @Query("SELECT * FROM repositories_table WHERE name = :repoName LIMIT 1")
    suspend fun getRepositoryByName(repoName: String): RepositoriesCache?
}