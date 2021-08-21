package com.example.moovix.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PeopleResponse(
    @SerializedName("page")
    var page: Int?, // 1
    @SerializedName("results")
    var results: List<PopularActors?>?,
    @SerializedName("total_pages")
    var totalPages: Int?, // 500
    @SerializedName("total_results")
    var totalResults: Int? // 10000
) {

}

data class PopularActors(
    @SerializedName("adult")
    var adult: Boolean?, // false
    @SerializedName("gender")
    var gender: Int?, // 1
    @SerializedName("id")
    var id: Int?, // 1245
    @SerializedName("known_for")
    var knownFor: List<KnownFor?>?,
    @SerializedName("known_for_department")
    var knownForDepartment: String?, // Acting
    @SerializedName("name")
    var name: String?, // Scarlett Johansson
    @SerializedName("popularity")
    var popularity: Double?, // 61.327
    @SerializedName("profile_path")
    var profilePath: String? // /6NsMbJXRlDZuDzatN2akFdGuTvx.jpg
) {

}

data class KnownFor(
    @SerializedName("adult")
    var adult: Boolean?, // false
    @SerializedName("backdrop_path")
    var backdropPath: String?, // /nNmJRkg8wWnRmzQDe2FwKbPIsJV.jpg
    @SerializedName("first_air_date")
    var firstAirDate: String?, // 1989-12-17
    @SerializedName("genre_ids")
    var genreIds: List<Int?>?,
    @SerializedName("id")
    var id: Int?, // 24428
    @SerializedName("media_type")
    var mediaType: String?, // movie
    @SerializedName("name")
    var name: String?, // The Simpsons
    @SerializedName("origin_country")
    var originCountry: List<String?>?,
    @SerializedName("original_language")
    var originalLanguage: String?, // en
    @SerializedName("original_name")
    var originalName: String?, // The Simpsons
    @SerializedName("original_title")
    var originalTitle: String?, // The Avengers
    @SerializedName("overview")
    var overview: String?, // When an unexpected enemy emerges and threatens global safety and security, Nick Fury, director of the international peacekeeping agency known as S.H.I.E.L.D., finds himself in need of a team to pull the world back from the brink of disaster. Spanning the globe, a daring recruitment effort begins!
    @SerializedName("poster_path")
    var posterPath: String?, // /RYMX2wcKCBAr24UyPD7xwmjaTn.jpg
    @SerializedName("release_date")
    var releaseDate: String?, // 2012-04-25
    @SerializedName("title")
    var title: String?, // The Avengers
    @SerializedName("video")
    var video: Boolean?, // false
    @SerializedName("vote_average")
    var voteAverage: Double?, // 7.7
    @SerializedName("vote_count")
    var voteCount: Int? // 24930
)