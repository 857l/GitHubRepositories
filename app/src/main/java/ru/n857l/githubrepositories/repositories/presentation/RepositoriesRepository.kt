package ru.n857l.githubrepositories.repositories.presentation

import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesDao

interface RepositoriesRepository {

    suspend fun data(): List<RepositoryItem>

    class Base(
        private val dao: RepositoriesDao
    ) : RepositoriesRepository {

        override suspend fun data(): List<RepositoryItem> {
            return dao.getAll().map { data ->
                RepositoryItem(
                    repositoryName = data.name,
                    programmingLanguage = data.language ?: "",
                    repositoryDescription = data.description ?: ""
                )
            }
        }
    }
}