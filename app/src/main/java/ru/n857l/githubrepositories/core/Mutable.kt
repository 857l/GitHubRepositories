package ru.n857l.githubrepositories.core

interface Mutable<T : Any> : Read<T>, Save<T>, Clear

interface Read<T : Any> {
    fun read(): T
}

interface Save<T : Any> {
    fun save(data: T)
}

interface Clear {
    fun clear()
}