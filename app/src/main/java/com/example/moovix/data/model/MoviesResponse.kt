package com.example.moovix.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.firebase.ui.auth.data.model.User
import java.io.Serializable

data class MoviesResponse(
    @SerializedName("dates")
    var dates: MovieDates?,
    @SerializedName("page")
    var page: Int?, // 1
    @SerializedName("results")
    var results: List<MovieModel>?,
    @SerializedName("total_pages")
    var totalPages: Int?, // 56
    @SerializedName("total_results")
    var totalResults: Int? // 1119
) {

}

data class MovieDates(
    @SerializedName("maximum")
    var maximum: String?, // 2021-07-04
    @SerializedName("minimum")
    var minimum: String? // 2021-05-17
)

data class MovieModel(
    @SerializedName("adult")
    var adult: Boolean? = null, // false
    @SerializedName("backdrop_path")
    var backdropPath: String? = null, // /620hnMVLu6RSZW6a5rwO8gqpt0t.jpg
    @SerializedName("genre_ids")
    var genreIds: List<Int?>? = null,
    @SerializedName("id")
    var id: Int? = null, // 508943
    @SerializedName("original_language")
    var originalLanguage: String? = null, // en
    @SerializedName("original_title")
    var originalTitle: String? = null, // Luca
    @SerializedName("overview")
    var overview: String? = null, // Luca and his best friend Alberto experience an unforgettable summer on the Italian Riviera. But all the fun is threatened by a deeply-held secret: they are sea monsters from another world just below the water’s surface.
    @SerializedName("popularity")
    var popularity: Double? = null, // 5577.472
    @SerializedName("poster_path")
    var posterPath: String? = null, // /jTswp6KyDYKtvC52GbHagrZbGvD.jpg
    @SerializedName("release_date")
    var releaseDate: String? = null, // 2021-06-17
    @SerializedName("title")
    var title: String? = null, // Luca
    @SerializedName("video")
    var video: Boolean? = null, // false
    @SerializedName("vote_average")
    var voteAverage: Double? = null, // 8.2
    @SerializedName("vote_count")
    var voteCount: Int? = null,
    var user: String? = null// 2107
) : Serializable {
    constructor(
        adult: Boolean?,
        backdropPath: String?,
        genreIds: List<Int?>?,
        id: Int?,
        originalTitle: String?,
        originalLanguage: String?,
        overview: String?,
        posterPath: String?,
        releaseDate: String?,
        title: String?,
        video: Boolean?,
        voteAverage: Double?,
        voteCount: Int?,
        user: String?
        ) : this() {
        this.user = user
        this.adult = adult
        this.backdropPath=backdropPath
        this.genreIds = genreIds
        this.id = id
        this.originalLanguage = originalLanguage
        this.originalTitle = originalTitle
        this.overview = overview
        this.popularity = popularity
        this.posterPath = posterPath
        this.releaseDate = releaseDate
        this.title = title
        this.video = video
        this.voteAverage = voteAverage
        this.voteCount = voteCount

    }
}