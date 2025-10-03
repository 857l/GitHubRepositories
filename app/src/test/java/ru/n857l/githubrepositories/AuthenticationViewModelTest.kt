package ru.n857l.githubrepositories

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationRepository
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationUiState
import ru.n857l.githubrepositories.authentication.presentation.AuthenticationViewModel

class AuthenticationViewModelTest {

    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var repository: FakeAuthenticationRepository

    @Before
    fun setup() {
        repository = FakeAuthenticationRepository()
        viewModel = AuthenticationViewModel(repository = repository)
    }

    @Test
    fun case1() {
        var actual: AuthenticationUiState = viewModel.init()
        var expected: AuthenticationUiState = AuthenticationUiState.WrongInput
        Assert.assertEquals(expected, actual)

        actual = viewModel.handleUserInput("1234567890")
        expected = AuthenticationUiState.SuccessInput
        Assert.assertEquals(expected, actual)

        actual = viewModel.handleUserInput("aaa")
        expected = AuthenticationUiState.SuccessInput
        Assert.assertEquals(expected, actual)

        actual = viewModel.handleUserInput("AAA")
        expected = AuthenticationUiState.SuccessInput
        Assert.assertEquals(expected, actual)

        actual = viewModel.handleUserInput("1a b2")
        expected = AuthenticationUiState.WrongInput
        Assert.assertEquals(expected, actual)

        actual = viewModel.handleUserInput("1a@b2")
        expected = AuthenticationUiState.WrongInput
        Assert.assertEquals(expected, actual)

        actual = viewModel.handleUserInput("1aФbФФ2")
        expected = AuthenticationUiState.WrongInput
        Assert.assertEquals(expected, actual)

        actual = viewModel.handleUserInput("Фhp_IqIMN0ZH6z0wIEB4T9A2g4E")
        expected = AuthenticationUiState.WrongInput
        Assert.assertEquals(expected, actual)

        actual = viewModel.handleUserInput("Ahp_IqIMN0ZH6z0wIEB4T9A2g4E")
        expected = AuthenticationUiState.SuccessInput
        Assert.assertEquals(expected, actual)
    }
}

private class FakeAuthenticationRepository : AuthenticationRepository {

    private var userInput: String = ""

    override fun clear() {
        userInput = ""
    }

    override fun data(): String = userInput

    override fun saveUserInput(value: String) {
        userInput = value
    }

    override fun tokenIsValid(text: String): Boolean {
        val regex = Regex("^[A-Za-z0-9_]+$")
        return regex.matches(text)
    }
}