package com.example.moovix.data

import com.example.moovix.KEY
import com.example.moovix.data.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing?api_key=${KEY}&language=en-US")
    fun getNowPlayingMovies(): Call<MoviesResponse>

    @GET("movie/top_rated?api_key=${KEY}&language=en-US")
    fun getTopRated(): Call<MoviesResponse>

    @GET("tv/popular?api_key=${KEY}&language=en-US&page=1")
    fun getPopularSeries(): Call<SeriesResponse>

    @GET("tv/top_rated?api_key=${KEY}&language=en-US&page=1")
    fun getTopRatedSeries(): Call<SeriesResponse>

    @GET("movie/upcoming?api_key=${KEY}&language=en-US&page=1")
    fun getUpcomingMovies(): Call<MoviesResponse>

    @GET("tv/on_the_air?api_key=${KEY}&language=en-US&page=1")
    fun getAiringToday(): Call<SeriesResponse>

    @GET("person/popular?api_key=${KEY}&language=en-US&page=1")
    fun getPopActors(): Call<PeopleResponse>

    @GET("search/movie?api_key=${KEY}&language=en-US&page=1&include_adult=false")
    fun getSearchMovies(@Query("query") query: String): Call<MovieSearchResponse>

    @GET("https://api.themoviedb.org/3/search/tv?api_key=${KEY}&language=en-US&page=1&include_adult=false")
    fun getSearchSeries(@Query("query") query: String): Call<MovieSearchResponse>

    @GET("https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key=${KEY}&language=en-US")
    fun getYoutubeMoviesResponse(@Path("movie_id") query: Int?): Call<YoutubeMovieResponse>

    @GET("https://api.themoviedb.org/3/movie/{movie_id}/similar?api_key=${KEY}&language=en-US&page=1")
    fun getSimilarMovies(@Path("movie_id") params: Int?): Call<MoviesResponse>

    @GET("https://api.themoviedb.org/3/tv/{tv_id}/videos?api_key=${KEY}&language=en-US")
    fun getYoutubeSeriesVideos(@Path("tv_id")params: Int?): Call<YoutubeMovieResponse>

    @GET("https://api.themoviedb.org/3/tv/{tv_id}/similar?api_key=${KEY}&language=en-US&page=1")
    fun getYoutubeSimilarSeries(@Path("tv_id")tvId: Int?): Call<SeriesResponse>
}

