package ru.n857l.githubrepositories.details.presentation

import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesDao

interface DetailsRepository {

    suspend fun data(name: String): RepositoriesCache?

    class Base(
        private val dao: RepositoriesDao
    ) : DetailsRepository {
        override suspend fun data(name: String): RepositoriesCache? =
            dao.getRepositoryByName(name)
    }
}