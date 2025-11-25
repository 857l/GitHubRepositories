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

    @Query("SELECT owner FROM repositories_table WHERE name = :repoName LIMIT 1")
    suspend fun getOwnerByName(repoName: String): String?

    @Query("SELECT readme FROM repositories_table WHERE name = :repoName LIMIT 1")
    suspend fun getReadmeByName(repoName: String): String

    @Query("UPDATE repositories_table SET readme = :readme WHERE name = :repoName")
    suspend fun updateReadme(repoName: String, readme: String)

    @Query("SELECT token FROM token_table LIMIT 1")
    fun readLastUsedToken(): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLastUsedToken(token: TokenEntity)
}