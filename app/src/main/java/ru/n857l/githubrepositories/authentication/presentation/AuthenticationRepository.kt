package ru.n857l.githubrepositories.authentication.presentation

interface AuthenticationRepository {

    fun clear()

    fun data(): String

    fun saveUserInput(value: String)

    fun tokenIsValid(text: String): Boolean

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
    }
}