package ru.n857l.githubrepositories.core

interface All<T : Any> : Read<T>, Save<T>

interface Read<T : Any> {
    fun read(): T
}

interface Save<T : Any> {
    fun save(data: T)
}