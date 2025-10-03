package ru.n857l.githubrepositories.repositories.presentation

interface RepositoriesRepository {

    fun repositoriesList(): List<RepositoryItemUiState>

    class Base(
        private val repositoriesList: List<RepositoryItemUiState> = listOf(
            RepositoryItemUiState(
                "moko-web3",
                "Kotlin",
                "Ethereum Web3 implementation"
            ),
            RepositoryItemUiState(
                "moko-web3",
                "Kotlin",
                "Template project of a Mobile (Android & iOS) Kotlin MultiPlatform project with the MOKO libraries and modularized architecture"
            )
        )
    ) : RepositoriesRepository {

        override fun repositoriesList(): List<RepositoryItemUiState> = repositoriesList
    }
}