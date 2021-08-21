package com.example.moovix.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import java.io.Serializable


data class YoutubeMovieResponse(
    @SerializedName("id")
    var id: Int?, // 459151
    @SerializedName("results")
    var results: List<VideoResult?>?
) {

}

data class VideoResult(
    @SerializedName("id")
    var id: String?, // 5fbd6538d9f4a6003d99a593
    @SerializedName("iso_3166_1")
    var iso31661: String?, // US
    @SerializedName("iso_639_1")
    var iso6391: String?, // en
    @SerializedName("key")
    var key: String?, // QPzy8Ckza08
    @SerializedName("name")
    var name: String?, // THE BOSS BABY: FAMILY BUSINESS | Official Trailer
    @SerializedName("site")
    var site: String?, // YouTube
    @SerializedName("size")
    var size: Int?, // 1080
    @SerializedName("type")
    var type: String? // Trailer
)
