package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.core.StringCache

interface AuthenticationRepository {

    fun clear()

    fun data(): String

    fun saveUserInput(value: String)

    class Base(
        private val token: StringCache
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
    }
}