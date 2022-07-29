package com.ramilkapev.moviereviews.data

import com.ramilkapev.moviereviews.domain.models.Movie
import com.ramilkapev.moviereviews.domain.models.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("svc/movies/v2/reviews/all.json")
    suspend fun getMovies(
        @Query("offset") offset: Int,
        @Query("api-key") apiKey: String
    ): Movie<Result>
}