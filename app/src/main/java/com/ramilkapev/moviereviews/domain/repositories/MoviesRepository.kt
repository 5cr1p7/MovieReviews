package com.ramilkapev.moviereviews.domain.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ramilkapev.moviereviews.data.MoviesPagingSource
import com.ramilkapev.moviereviews.data.Network
import com.ramilkapev.moviereviews.domain.models.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor() {

    fun getMoviesFlow(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { MoviesPagingSource(Network.service) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}