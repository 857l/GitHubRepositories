package ru.n857l.githubrepositories.details.presentation

import ru.n857l.githubrepositories.views.text.TextUiState
import ru.n857l.githubrepositories.views.text.UpdateTextView
import java.io.Serializable

interface DetailsUiState : Serializable {

    fun update(
        linkTextView: UpdateTextView,
        licenseTextView: UpdateTextView,
        startsTextView: UpdateTextView,
        forksTextView: UpdateTextView,
        watchersTextView: UpdateTextView,
        readmeTextView: UpdateTextView
    ) = Unit

    object Empty : DetailsUiState

    abstract class Abstract(
        private val linkUiState: TextUiState,
        private val licenseUiState: TextUiState,
        private val startsUiState: TextUiState,
        private val forksUiState: TextUiState,
        private val watchersUiState: TextUiState,
        private val readmeUiState: TextUiState
    ) : DetailsUiState {
        override fun update(
            linkTextView: UpdateTextView,
            licenseTextView: UpdateTextView,
            startsTextView: UpdateTextView,
            forksTextView: UpdateTextView,
            watchersTextView: UpdateTextView,
            readmeTextView: UpdateTextView
        ) {
            linkTextView.update(linkUiState)
            licenseTextView.update(licenseUiState)
            startsTextView.update(startsUiState)
            forksTextView.update(forksUiState)
            watchersTextView.update(watchersUiState)
            readmeTextView.update(readmeUiState)
        }
    }

    data class Show(
        private val link: String,
        private val license: String,
        private val stars: String,
        private val forks: String,
        private val watchers: String,
        private val readme: String
    ) : Abstract(
        TextUiState.Show(link),
        TextUiState.Show(license),
        TextUiState.Show(stars),
        TextUiState.Show(forks),
        TextUiState.Show(watchers),
        TextUiState.Show(readme)
    )
}