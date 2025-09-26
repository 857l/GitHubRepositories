package ru.n857l.githubrepositories.authentication.presentation

import ru.n857l.githubrepositories.core.StringCache

interface AuthenticationRepository {

    fun clear()

    fun data(): String

    class Base(
        private val token: StringCache
    ) : AuthenticationRepository {

        override fun data(): String {
            return token.read()
        }

        override fun clear() {
            token.save("")
        }
    }
}