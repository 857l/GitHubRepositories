package ru.n857l.githubrepositories.authentication

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationRepository
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationUiState
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationViewModel
import ru.n857l.githubrepositories.authentication.presentation.data.LoadResult
import ru.n857l.githubrepositories.core.RunAsync
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.core.di.ClearViewModel
import ru.n857l.githubrepositories.di.MyViewModel

class AuthenticationViewModelTest {

    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var repository: FakeAuthenticationRepository
    private lateinit var clearViewModel: FakeClearViewModel
    private lateinit var observable: FakeUiObservableAuthentication
    private lateinit var runAsync: FakeRunAsync
    private lateinit var fragment: FakeFragment

    @Before
    fun setup() {
        repository = FakeAuthenticationRepository()
        clearViewModel = FakeClearViewModel()
        observable = FakeUiObservableAuthentication()
        runAsync = FakeRunAsync()
        viewModel = AuthenticationViewModel(
            repository = repository,
            clearViewModel = clearViewModel,
            observable = observable,
            runAsync = runAsync
        )
        fragment = FakeFragment()
    }

    @Test
    fun handleInput() {
        repository.expectResult(LoadResult.Success)

        viewModel.init()
        assertEquals(
            AuthenticationUiState.Initial(""),
            observable.postUiStateCalledList.last()
        )

        viewModel.handleUserInput("1234567890")
        assertEquals(
            AuthenticationUiState.SuccessInput,
            observable.postUiStateCalledList.last()
        )

        viewModel.handleUserInput("aaa")
        assertEquals(
            AuthenticationUiState.SuccessInput,
            observable.postUiStateCalledList.last()
        )

        viewModel.handleUserInput("AAA")
        assertEquals(
            AuthenticationUiState.SuccessInput,
            observable.postUiStateCalledList.last()
        )

        viewModel.handleUserInput("1a b2")
        assertEquals(
            AuthenticationUiState.WrongInput,
            observable.postUiStateCalledList.last()
        )

        viewModel.handleUserInput("1a@b2")
        assertEquals(
            AuthenticationUiState.WrongInput,
            observable.postUiStateCalledList.last()
        )

        viewModel.handleUserInput("1aФbФФ2")
        assertEquals(
            AuthenticationUiState.WrongInput,
            observable.postUiStateCalledList.last()
        )

        viewModel.handleUserInput("Фhp_IqIMN0ZH6z0wIEB4T9A2g4E")
        assertEquals(
            AuthenticationUiState.WrongInput,
            observable.postUiStateCalledList.last()
        )

        viewModel.handleUserInput("Ahp_IqIMN0ZH6z0wIEB4T9A2g4E")
        assertEquals(
            AuthenticationUiState.SuccessInput,
            observable.postUiStateCalledList.last()
        )
    }

    @Test
    fun sameFragment() {
        repository.expectResult(LoadResult.Success)

        viewModel.load(isFirstRun = true)
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(AuthenticationUiState.Load, observable.postUiStateCalledList.first())
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment)
        assertEquals(1, observable.registerCalledCount)
        assertEquals(AuthenticationUiState.Load, fragment.statesList.first())
        assertEquals(1, fragment.statesList.size)

        runAsync.returnResult()
        assertEquals(AuthenticationUiState.Success, observable.postUiStateCalledList.last())
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(AuthenticationUiState.Success, fragment.statesList.last())
        assertEquals(2, fragment.statesList.size)

        assertEquals(1, clearViewModel.clearCalledCount)
    }

    @Test
    fun recreateActivity() {
        repository.expectResult(LoadResult.Error("invalid token"))

        viewModel.load(isFirstRun = true)
        assertEquals(AuthenticationUiState.Load, observable.postUiStateCalledList.first())
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment)
        assertEquals(AuthenticationUiState.Load, fragment.statesList.first())
        assertEquals(1, fragment.statesList.size)

        viewModel.stopUpdates()
        assertEquals(1, observable.unregisterCalledCount)

        runAsync.returnResult()
        assertEquals(
            AuthenticationUiState.Error("invalid token"),
            observable.postUiStateCalledList.last()
        )
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(1, fragment.statesList.size)

        val newFragment = FakeFragment()

        viewModel.load(isFirstRun = false)
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = newFragment)
        assertEquals(2, observable.registerCalledCount)
        assertEquals(
            AuthenticationUiState.Error("invalid token"),
            newFragment.statesList.first()
        )
        assertEquals(1, newFragment.statesList.size)
    }
}

private class FakeAuthenticationRepository : AuthenticationRepository {

    private var userInput: String = ""

    override fun clear() {
        userInput = ""
    }

    override fun token(): String = userInput

    override fun saveUserInput(value: String) {
        userInput = value
    }

    override fun tokenIsValid(text: String): Boolean {
        val regex = Regex("^[A-Za-z0-9_]+$")
        return regex.matches(text)
    }

    private var loadResult: LoadResult? = null
    fun expectResult(loadResult: LoadResult) {
        this.loadResult = loadResult
    }

    var loadCalledCount = 0
    override suspend fun load(): LoadResult {
        loadCalledCount++
        return loadResult!!
    }
}

class FakeClearViewModel : ClearViewModel {

    var clearCalledCount = 0

    override fun clear(viewModelClass: Class<out MyViewModel>) {
        clearCalledCount++
    }
}

private class FakeUiObservableAuthentication : UiObservable<AuthenticationUiState> {

    private var uiStateCached: AuthenticationUiState? = null
    private var observerCached: ((AuthenticationUiState) -> Unit)? = null

    var registerCalledCount = 0
    override fun register(observer: (AuthenticationUiState) -> Unit) {
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

    val postUiStateCalledList = mutableListOf<AuthenticationUiState>()

    override fun postUiState(uiState: AuthenticationUiState) {
        postUiStateCalledList.add(uiState)
        if (observerCached == null) {
            uiStateCached = uiState
        } else {
            observerCached?.invoke(uiState)
            uiStateCached = null
        }
    }
}

@Suppress("UNCHECKED_CAST")
private class FakeRunAsync : RunAsync {

    private var result: Any? = null
    private var ui: (Any) -> Unit = {}

    override fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    ) = runBlocking {
        result = heavyOperation.invoke()
        ui = uiUpdate as (Any) -> Unit
    }

    fun returnResult() {
        ui.invoke(result!!)
    }
}

private class FakeFragment : (AuthenticationUiState) -> Unit {

    val statesList = mutableListOf<AuthenticationUiState>()

    override fun invoke(p1: AuthenticationUiState) {
        statesList.add(p1)
    }
}