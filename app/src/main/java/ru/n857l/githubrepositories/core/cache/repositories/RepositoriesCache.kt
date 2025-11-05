package ru.n857l.githubrepositories.core.cache.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories_table")
data class RepositoriesCache(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "html_url") val htmlUrl: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "license") val license: String?,
    @ColumnInfo(name = "forks_count") val forks: Int,
    @ColumnInfo(name = "stargazers_count") val stars: Int,
    @ColumnInfo(name = "watchers_count") val watchers: Int,
)