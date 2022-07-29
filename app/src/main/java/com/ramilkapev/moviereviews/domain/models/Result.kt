package com.ramilkapev.moviereviews.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "display_title")
    val displayTitle: String?,
    @Json(name = "multimedia")
    val multimedia: Multimedia?,
    @Json(name = "summary_short")
    val summaryShort: String?
)