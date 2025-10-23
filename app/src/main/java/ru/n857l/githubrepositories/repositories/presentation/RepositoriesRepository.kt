package ru.n857l.githubrepositories.repositories.presentation

interface RepositoriesRepository {

    fun data(): List<RepositoryItem>

    class Base(
        private val repositoriesList: List<RepositoryItem>
    ) : RepositoriesRepository {

        override fun data(): List<RepositoryItem> = repositoriesList
    }
}