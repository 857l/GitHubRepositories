package ru.n857l.githubrepositories.core

import java.io.Serializable

interface UiObservable<T : Serializable> {

    fun register(observer: (T) -> Unit)

    fun unregister()

    fun postUiState(uiState: T)

    class Base<T : Serializable> : UiObservable<T> {

        private var uiStateCached: T? = null
        private var observerCached: ((T) -> Unit)? = null

        override fun register(observer: (T) -> Unit) {
            observerCached = observer
            uiStateCached?.let {
                observerCached?.invoke(it)
                uiStateCached = null
            }
        }

        override fun unregister() {
            observerCached = null
        }


        override fun postUiState(uiState: T) {
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached?.invoke(uiState)
                uiStateCached = null
            }
        }
    }
}