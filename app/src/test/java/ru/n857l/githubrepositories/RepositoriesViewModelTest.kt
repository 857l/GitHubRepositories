package ru.n857l.githubrepositories

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesRepository
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesUiState
import ru.n857l.githubrepositories.repositories.presentation.RepositoriesViewModel
import ru.n857l.githubrepositories.repositories.presentation.RepositoryItem

//TODO TEST
class RepositoriesViewModelTest {

    private lateinit var viewModel: RepositoriesViewModel
    private lateinit var repository: FakeRepositoriesRepository
    private lateinit var clearViewModel: FakeClearViewModel
    private lateinit var observable: FakeUiObservableRepositories

    @Before
    fun setup() {
        clearViewModel = FakeClearViewModel()
        observable = FakeUiObservableRepositories()
    }

    @Test
    fun case1() {
        repository = FakeRepositoriesRepository.Filled
        viewModel = RepositoriesViewModel(
            repository = repository,
            clearViewModel = clearViewModel,
            observable = observable
        )

        viewModel.init()
        assertEquals(
            RepositoriesUiState.Empty,
            observable.postUiStateCalledList.last()
        )

        assertEquals(
            listOf(
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
            ),
            viewModel.repositoriesList()
        )

        repository = FakeRepositoriesRepository.Empty
        viewModel = RepositoriesViewModel(
            repository = repository,
            clearViewModel = clearViewModel,
            observable = observable
        )

        viewModel.init()
        assertEquals(
            RepositoriesUiState.EmptyRepositories,
            observable.postUiStateCalledList.last()
        )

        assertEquals(
            listOf<RepositoryItem>(),
            viewModel.repositoriesList()
        )
    }
}

interface FakeRepositoriesRepository : RepositoriesRepository {

    abstract class Abstract(
        private val repository: List<RepositoryItem>
    ) : FakeRepositoriesRepository {

        override fun data(): List<RepositoryItem> = repository
    }

    object Empty : Abstract(
        listOf<RepositoryItem>()
    )

    object Filled : Abstract(
        listOf(
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
    )
}

private class FakeUiObservableRepositories : UiObservable<RepositoriesUiState> {

    private var uiStateCached: RepositoriesUiState? = null
    private var observerCached: ((RepositoriesUiState) -> Unit)? = null

    var registerCalledCount = 0
    override fun register(observer: (RepositoriesUiState) -> Unit) {
        registerCalledCount++
        observerCached = observer
        uiStateCached?.let {
            observerCached?.invoke(it)
            uiStateCached = null
        }
    }

    var unregisterCalledCount = 0
    override fun unregister() {
        unregisterCalledCount++
        observerCached = null
    }

    val postUiStateCalledList = mutableListOf<RepositoriesUiState>()

    override fun postUiState(uiState: RepositoriesUiState) {
        postUiStateCalledList.add(uiState)
        if (observerCached == null) {
            uiStateCached = uiState
        } else {
            observerCached?.invoke(uiState)
            uiStateCached = null
        }
    }
}