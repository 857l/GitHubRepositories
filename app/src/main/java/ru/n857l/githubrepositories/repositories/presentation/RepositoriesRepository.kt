package ru.n857l.githubrepositories.repositories.presentation

interface RepositoriesRepository {

    fun repositoriesList(): MutableList<RepositoryItem>

    fun updateRepositoriesList(repositoriesList: MutableList<RepositoryItem>)

    class Base(
        private var repositoriesList: MutableList<RepositoryItem> = mutableListOf(
            RepositoryItem(
                "moko-web3",
                "Kotlin",
                "Ethereum Web3 implementation"
            ),
            RepositoryItem(
                "moko-web3",
                "Kotlin",
                "Template project of a Mobile (Android & iOS) Kotlin MultiPlatform project with the MOKO libraries and modularized architecture"
            )
        )
    ) : RepositoriesRepository {

        override fun repositoriesList(): MutableList<RepositoryItem> = repositoriesList

        override fun updateRepositoriesList(repositoriesList: MutableList<RepositoryItem>) {
            this.repositoriesList = repositoriesList
        }
    }
}