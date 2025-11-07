package ru.n857l.githubrepositories.details

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
        //observable.postUiState(DetailsUiState.Progress)
        runAsync.handleAsync(viewModelScope, {
            val data = repository.data(numberDetails.read())
            if (data != null) {
                DetailsUiState.Show(
                    data.htmlUrl,
                    data.license ?: "",
                    data.stars.toString(),
                    data.forks.toString(),
                    data.watchers.toString(),
                    "Readme"//TODO README
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