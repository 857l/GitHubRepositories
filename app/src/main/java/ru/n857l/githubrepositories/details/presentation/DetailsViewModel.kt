package ru.n857l.githubrepositories.details.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.n857l.githubrepositories.core.RunAsync
import ru.n857l.githubrepositories.core.UiObservable
import ru.n857l.githubrepositories.core.di.ClearViewModel
import ru.n857l.githubrepositories.di.MyViewModel

class DetailsViewModel(
    private val repository: DetailsRepository,
    private val clearViewModel: ClearViewModel,
    private val observable: UiObservable<DetailsUiState>,
    private val runAsync: RunAsync,
    private val numberDetails: NumberDetails.Read
) : MyViewModel {

    override fun clear() {
        clearViewModel.clear(DetailsViewModel::class.java)
    }

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init() {
        runAsync.handleAsync(viewModelScope, {
            val repoName = numberDetails.read()
            val data = repository.data(repoName)
            if (data != null) {
                DetailsUiState.Show(
                    data.htmlUrl,
                    data.license ?: "",
                    data.stars.toString(),
                    data.forks.toString(),
                    data.watchers.toString(),
                )
            } else {
                DetailsUiState.Empty
            }
        }) {
            observable.postUiState(it)
        }
        loadReadme()
    }

    private fun loadReadme() {
        runAsync.handleAsync(viewModelScope, {
            val repoName = numberDetails.read()
            val result = repository.load(repoName)
            if (result.isSuccessful()) {
                DetailsUiState.ShowReadme(
                    repository.readme(repoName)
                )
            } else {
                DetailsUiState.Empty
            }
        }) {
            observable.postUiState(it)
        }
    }

    fun startUpdates(observer: (DetailsUiState) -> Unit) =
        observable.register(observer)

    fun stopUpdates() = observable.unregister()
}