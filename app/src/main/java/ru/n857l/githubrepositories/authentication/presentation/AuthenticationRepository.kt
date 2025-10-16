package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.authentication.presentation.data.LoadResult

interface AuthenticationRepository {

    fun clear()

    fun data(): String

    fun saveUserInput(value: String)

    fun tokenIsValid(text: String): Boolean

    fun load(resultCallback: (LoadResult) -> Unit)

    class Base(
        private val token: TokenCache,
    ) : AuthenticationRepository {

        override fun data(): String {
            return token.read()
        }

        override fun saveUserInput(value: String) {
            token.save(value)
        }

        override fun clear() {
            token.save("")
        }

        override fun tokenIsValid(text: String): Boolean {
            val regex = Regex("^[A-Za-z0-9_]+$")
            return regex.matches(text)
        }

        override fun load(resultCallback: (LoadResult) -> Unit) {
            TODO("Not yet implemented")
        }
    }
}