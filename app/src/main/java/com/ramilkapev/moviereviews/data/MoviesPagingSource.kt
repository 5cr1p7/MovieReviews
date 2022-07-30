package com.ramilkapev.moviereviews.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ramilkapev.moviereviews.BuildConfig
import com.ramilkapev.moviereviews.domain.models.Result
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(
    private val service: MoviesService
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val pageNumber = params.key ?: 0

            val response = service.getMovies(pageNumber, BuildConfig.API_KEY)

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null

            val nextKey = if (response.results.isNotEmpty()) pageNumber + 20 else null
            LoadResult.Page(
                data = response.results,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}