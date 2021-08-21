package com.example.moovix.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


data class TopRatedMoviesResponse(
    @SerializedName("page")
    var page: Int?, // 1
    @SerializedName("results")
    var results: List<Result?>?,
    @SerializedName("total_pages")
    var totalPages: Int?, // 443
    @SerializedName("total_results")
    var totalResults: Int? // 8860
) {
}

data class Result(
    @SerializedName("adult")
    var adult: Boolean?, // false
    @SerializedName("backdrop_path")
    var backdropPath: String?, // /gNBCvtYyGPbjPCT1k3MvJuNuXR6.jpg
    @SerializedName("genre_ids")
    var genreIds: List<Int?>?,
    @SerializedName("id")
    var id: Int?, // 19404
    @SerializedName("original_language")
    var originalLanguage: String?, // hi
    @SerializedName("original_title")
    var originalTitle: String?, // दिलवाले दुल्हनिया ले जायेंगे
    @SerializedName("overview")
    var overview: String?, // Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.
    @SerializedName("popularity")
    var popularity: Double?, // 16.673
    @SerializedName("poster_path")
    var posterPath: String?, // /2CAL2433ZeIihfX1Hb2139CX0pW.jpg
    @SerializedName("release_date")
    var releaseDate: String?, // 1995-10-20
    @SerializedName("title")
    var title: String?, // Dilwale Dulhania Le Jayenge
    @SerializedName("video")
    var video: Boolean?, // false
    @SerializedName("vote_average")
    var voteAverage: Double?, // 8.7
    @SerializedName("vote_count")
    var voteCount: Int? // 2991
)
