package com.example.moovix.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


data class MovieSearchResponse(
    @SerializedName("page")
    var page: Int?, // 1
    @SerializedName("results")
    var results: List<MoviesSearchResult?>?,
    @SerializedName("total_pages")
    var totalPages: Int?, // 1
    @SerializedName("total_results")
    var totalResults: Int? // 13
) {

}

data class MoviesSearchResult(
    @SerializedName("adult")
    var adult: Boolean?, // false
    @SerializedName("backdrop_path")
    var backdropPath: String?, // /1ZNLJkYSfRcPconjUYa5pReoLH9.jpg
    @SerializedName("genre_ids")
    var genreIds: List<Int?>?,
    @SerializedName("id")
    var id: Int?, // 62717
    @SerializedName("original_language")
    var originalLanguage: String?, // pt
    @SerializedName("original_title")
    var originalTitle: String?, // Loki - Arnaldo Baptista
    @SerializedName("overview")
    var overview: String?, // Loki brings the trajectory of Arnaldo Baptista from childhood, passing through the most successful phase as leader of the Mutantes, marriage to singer Rita Lee and then separation. He also goes through the depression that devastated his life after the group ended and that led him to attempt suicide, his solo career, rapprochement with his brother and member of the Mutantes, Sérgio Dias, culminating in the band's return in 2006.
    @SerializedName("popularity")
    var popularity: Double?, // 132.973
    @SerializedName("poster_path")
    var posterPath: String?, // /cYl39kCMjP37e39CbJqxWHvf1ez.jpg
    @SerializedName("release_date")
    var releaseDate: String?, // 2008-10-17
    @SerializedName("title")
    var title: String?, // Loki - Arnaldo Baptista
    @SerializedName("video")
    var video: Boolean?, // false
    @SerializedName("vote_average")
    var voteAverage: Double?, // 6.9
    @SerializedName("vote_count")
    var voteCount: Int? // 9
)
