package com.ramilkapev.moviereviews.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie<T>(
    @Json(name = "has_more")
    val hasMore: Boolean?,
    @Json(name = "num_results")
    val numResults: Int?,
    @Json(name = "results")
    val results: List<T>,
    @Json(name = "status")
    val status: String?
)