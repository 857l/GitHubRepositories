package ru.n857l.githubrepositories.repositories.presentation

interface RepositoriesRepository {

    fun repositoriesList(): List<RepositoryItem>


    class Base(
        private val repositoriesList: List<RepositoryItem> = listOf(
            RepositoryItem(
                "moko-web3",
                "Kotlin",
                "Ethereum Web3 implementation"
            ),
            RepositoryItem(
                "moko-web3",
                "Objective-C++",
                "Template project of a Mobile (Android & iOS) Kotlin MultiPlatform project with the MOKO libraries and modularized architecture"
            )
        )
    ) : RepositoriesRepository {

        override fun repositoriesList(): List<RepositoryItem> = repositoriesList
    }
}