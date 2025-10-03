package ru.n857l.githubrepositories

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesRepository
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesUiState
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesViewModel
import ru.n857l.githubrepositories.repositories.presentation.RepositoryItem

class RepositoriesViewModelTest {

    private lateinit var viewModel: RepositoriesViewModel
    private lateinit var repository: FakeRepositoriesRepository

    @Before
    fun setup() {
        repository = FakeRepositoriesRepository()
        viewModel = RepositoriesViewModel(repository = repository)
    }

    @Test
    fun case1() {
        var actual: RepositoriesUiState = viewModel.init()
        var expected: RepositoriesUiState = RepositoriesUiState.EmptyRepositories
        Assert.assertEquals(expected, actual)

        viewModel.addRepositoriesItem(
            RepositoryItem(
                "moko-web3",
                "Kotlin",
                "Ethereum Web3 implementation"
            )
        )

        actual = viewModel.init()
        expected = RepositoriesUiState.Show(repository)
        Assert.assertEquals(expected, actual)
    }
}

private class FakeRepositoriesRepository : RepositoriesRepository {

    private var repositoriesList: MutableList<RepositoryItem> = mutableListOf(

    )

    override fun repositoriesList(): MutableList<RepositoryItem> = repositoriesList

    override fun updateRepositoriesList(repositoriesList: MutableList<RepositoryItem>) {
        this.repositoriesList = repositoriesList
    }
}