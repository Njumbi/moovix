package com.example.moovix.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SeriesResponse(
    @SerializedName("page")
    var page: Int?, // 1
    @SerializedName("results")
    var results: List<SeriesResult?>?,
    @SerializedName("total_pages")
    var totalPages: Int?, // 96
    @SerializedName("total_results")
    var totalResults: Int? // 1901
) {

}

data class SeriesResult(
    @SerializedName("backdrop_path")
    var backdropPath: Any?=null, // null
    @SerializedName("first_air_date")
    var firstAirDate: String?=null, // 2004-05-10
    @SerializedName("genre_ids")
    var genreIds: List<Int?>?=null,
    @SerializedName("id")
    var id: Int?=null, // 100
    @SerializedName("name")
    var name: String?=null, // I Am Not an Animal
    @SerializedName("origin_country")
    var originCountry: List<String?>?=null,
    @SerializedName("original_language")
    var originalLanguage: String?=null, // en
    @SerializedName("original_name")
    var originalName: String?=null, // I Am Not an Animal
    @SerializedName("overview")
    var overview: String?=null, // I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists.
    @SerializedName("popularity")
    var popularity: Double?=null, // 10.265
    @SerializedName("poster_path")
    var posterPath: String?=null, // /qG59J1Q7rpBc1dvku4azbzcqo8h.jpg
    @SerializedName("vote_average")
    var voteAverage: Double?=null, // 9.3
    @SerializedName("vote_count")
    var voteCount: Int?=null, // 633
    var user: String? = null// 2107
) : Serializable{
    constructor(

        backdropPath: Any?,
        firstAirDate: String?,
        user: String?,
        genreIds: List<Int?>?,
        id: Int?,
        name: String?,
        originCountry: List<String?>?,
        originalLanguage: String?,
        originalName: String?,
        overview: String?,
        popularity: Double?,
        posterPath: String?,
        voteAverage: Double?,
        voteCount: Int?,




        ) : this(){
        this.backdropPath =backdropPath
        this.firstAirDate = firstAirDate
        this.genreIds =genreIds
        this.id =id
        this.name = name
        this.originCountry = originCountry
        this.originalLanguage =originalLanguage
        this.originalName =originalName
        this.overview = overview
        this.popularity = popularity
        this.posterPath = posterPath
        this.voteAverage = voteAverage
        this.voteCount = voteCount
        this.user = user

    }
}
