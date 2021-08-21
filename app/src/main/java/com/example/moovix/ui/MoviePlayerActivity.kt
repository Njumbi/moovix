package com.example.moovix.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.ApiClient
import com.example.moovix.data.model.*
import com.example.moovix.ui.adapters.SimilarMoviesAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.annotations.SerializedName
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import io.grpc.InternalChannelz.id
import kotlinx.android.synthetic.main.activity_movie_player.*
import kotlinx.android.synthetic.main.activity_movies_search.*
import kotlinx.android.synthetic.main.item_now_playing.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable


class MoviePlayerActivity : AppCompatActivity() {

    private lateinit var movieModel: MovieModel
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_player)

        db = Firebase.firestore
        auth = FirebaseAuth.getInstance()

        likeListener()

        movieModel = intent.getSerializableExtra("data") as MovieModel
        similarMoviesAdapter = SimilarMoviesAdapter()


        setSupportActionBar(toolbar3)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        rv_similar_movies.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_similar_movies.adapter = similarMoviesAdapter

        tv_movie_video_player_title.text = movieModel.title
        tv_movie_player_desc.text = movieModel.overview
        tv_movie_player_title.text = movieModel.title


        Glide
            .with(this)
            .load("https://image.tmdb.org/t/p/w500/" + movieModel.posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(iv_movie_video_player);

        getVideos(movieModel.id)
        getSimilarVideos(movieModel.id)

        iv_favorite.setOnClickListener {
            if (iv_favorite.drawable.constantState == this.resources.getDrawable(R.drawable.baseline_favorite_red_500_24dp).constantState) {
                val mDrawable: Drawable =
                    this.resources.getDrawable(R.drawable.baseline_favorite_border_black_24dp)
                iv_favorite.setImageDrawable(mDrawable)

                unLikeContent()
            } else {
                val mDrawable: Drawable =
                    this.resources.getDrawable(R.drawable.baseline_favorite_red_500_24dp)
                iv_favorite.setImageDrawable(mDrawable)
                likeContent()
            }
        }
    }

    private fun isUserLogged(): Boolean {
        return if (auth.currentUser == null) {
            Toast.makeText(
                this, "Login to continue", Toast.LENGTH_SHORT
            ).show()
            false
        } else
            true
    }

    private fun likeContent() {
        if (!isUserLogged()) return

        val data = movieModel
        data.user = auth.currentUser?.uid

        db.collection("favoriteMovies")
            .document()
            .set(data)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot added with ID: ")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun likeListener() {
        if (!isUserLogged()) return

        db.collection("favoriteMovies")
            .whereEqualTo("user", auth.currentUser?.uid)
            .addSnapshotListener { snapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                for ( doc in snapshot!! ){
                    val o = doc.toObject(MovieModel::class.java)
                    if (o.id == movieModel.id){
                        val mDrawable: Drawable =
                            this.resources.getDrawable(R.drawable.baseline_favorite_red_500_24dp)
                        iv_favorite.setImageDrawable(mDrawable)
                        break
                    }
                }
            };
    }

    private fun unLikeContent() {
        if (!isUserLogged()) return

        db.collection("favoriteMovies")
            .whereEqualTo("id", movieModel.id)
            .get()
            .addOnSuccessListener {
                val doc = it.documents.first()
                db.collection("favoriteMovies").document(doc.id).delete().addOnSuccessListener {
                    Log.w(TAG, "doc deleted")
                }
                    .addOnFailureListener {
                        Log.w(TAG, "Error deleting document", it)
                    }
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

    }

    private fun getSimilarVideos(params: Int?) {
        var call = ApiClient().service?.getSimilarMovies(params)
        call?.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.results
                    similarMoviesAdapter.setData(list!!)
                } else {
                    Toast.makeText(
                        this@MoviePlayerActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Toast.makeText(this@MoviePlayerActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getVideos(query: Int?) {
        pb_movie_player.visibility = View.VISIBLE
        youtube_player_view.visibility = View.GONE

        var call = ApiClient().service?.getYoutubeMoviesResponse(query)
        call?.enqueue(object : retrofit2.Callback<YoutubeMovieResponse> {
            override fun onResponse(
                call: Call<YoutubeMovieResponse>,
                response: Response<YoutubeMovieResponse>
            ) {

                pb_movie_player.visibility = View.GONE

                if (response.isSuccessful) {
                    val list = response.body()?.results

                    if (list != null) {
                        for (vr in list) {
                            if (vr!!.site == "YouTube") {
                                play(vr.key!!)
                                break
                            }
                        }
                    }

                } else {
                    Toast.makeText(
                        this@MoviePlayerActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<YoutubeMovieResponse>, t: Throwable) {
                Toast.makeText(this@MoviePlayerActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun play(id: String) {
        youtube_player_view.visibility = View.VISIBLE

        lifecycle.addObserver(youtube_player_view);
        youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(id, 0F);
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }

}