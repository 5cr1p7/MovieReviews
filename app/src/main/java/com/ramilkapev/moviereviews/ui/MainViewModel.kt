package com.ramilkapev.moviereviews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ramilkapev.moviereviews.domain.models.Result
import com.ramilkapev.moviereviews.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private var _moviesFlow: Flow<PagingData<Result>>? = null

    val moviesFlow: Flow<PagingData<Result>>?
        get() = _moviesFlow

    private var currentSearchJob: Job? = null

    fun getMovies() {
        currentSearchJob?.cancel()

        currentSearchJob = viewModelScope.launch {
            runCatching {
                repository.getMoviesFlow()
            }.onSuccess {
                _moviesFlow = it
            }
        }
    }

}