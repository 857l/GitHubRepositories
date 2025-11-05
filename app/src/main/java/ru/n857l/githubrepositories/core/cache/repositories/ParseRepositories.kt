package ru.n857l.githubrepositories.core.cache.repositories

import ru.n857l.githubrepositories.cloud_datasource.RepositoryCloud
import ru.n857l.githubrepositories.repositories.presentation.RepositoryItem

interface ParseRepositories {

    fun map(data: List<RepositoryCloud>): List<RepositoryItem>

    class Base : ParseRepositories {

        override fun map(data: List<RepositoryCloud>): List<RepositoryItem> {
            return data.map { repo ->
                RepositoryItem(
                    repositoryName = repo.name ?: "",
                    programmingLanguage = repo.language ?: "",
                    repositoryDescription = repo.description ?: ""
                )
            }
        }
    }
}