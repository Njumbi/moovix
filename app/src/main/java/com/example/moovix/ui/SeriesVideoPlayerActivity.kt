package com.example.moovix.ui

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.ApiClient
import com.example.moovix.data.model.*
import com.example.moovix.ui.adapters.SimilarSeries
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.oAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_movie_player.*
import kotlinx.android.synthetic.main.activity_series_video_player.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeriesVideoPlayerActivity : AppCompatActivity() {

    lateinit var seriesResult: SeriesResult
    lateinit var similarSeries: SimilarSeries
    lateinit var auth: FirebaseAuth
     lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_video_player)

        db = Firebase.firestore
        auth = FirebaseAuth.getInstance()

        likeListener()

        setSupportActionBar(toolbar4)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""

        seriesResult = intent.getSerializableExtra("seriesData") as SeriesResult

        similarSeries = SimilarSeries()
        rv_similar_series.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_similar_series.adapter = similarSeries

        tv_series_video_player_title.text = seriesResult.name
        tv_series_player_title.text = seriesResult.name
        tv_series_player_desc.text = seriesResult.overview

        Glide
            .with(this)
            .load("https://image.tmdb.org/t/p/w500/" + seriesResult.posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(iv_series_video_player);

        getYoutubeVideos(seriesResult.id)
        getSimilarVideos(seriesResult.id)

        ib_favorite_series.setOnClickListener {
            if (ib_favorite_series.drawable.constantState == this.resources.getDrawable(R.drawable.baseline_favorite_red_500_24dp).constantState) {
                val mDrawable: Drawable =
                    this.resources.getDrawable(R.drawable.baseline_favorite_border_black_24dp)
                ib_favorite_series.setImageDrawable(mDrawable)

                unLikeContent()
            } else {
                val mDrawable: Drawable =
                    this.resources.getDrawable(R.drawable.baseline_favorite_red_500_24dp)
                ib_favorite_series.setImageDrawable(mDrawable)
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

        val data = seriesResult
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
                    if (o.id == seriesResult.id){
                        val mDrawable: Drawable =
                            this.resources.getDrawable(R.drawable.baseline_favorite_red_500_24dp)
                        ib_favorite_series.setImageDrawable(mDrawable)
                        break
                    }
                }
            };
    }


    private fun unLikeContent() {
        if (!isUserLogged()) return

        db.collection("favoriteMovies")
            .whereEqualTo("id", seriesResult.id)
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

    private fun getYoutubeVideos(params: Int?) {
        pb_series_player.visibility = View.VISIBLE
        youtube_series_player_view.visibility = View.GONE
        var call = ApiClient()?.service?.getYoutubeSeriesVideos(params)
        call?.enqueue(object : Callback<YoutubeMovieResponse> {
            override fun onResponse(
                call: Call<YoutubeMovieResponse>,
                response: Response<YoutubeMovieResponse>
            ) {
                pb_series_player.visibility = View.GONE
                if (response.isSuccessful) {
                    var list = response.body()?.results
                    if (list != null) {
                        for (vr in list) {
                            if (vr?.site == "YouTube") {
                                play(vr.key!!)
                                break
                            }
                        }
                    }
                } else
                    Toast.makeText(
                        this@SeriesVideoPlayerActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onFailure(call: Call<YoutubeMovieResponse>, t: Throwable) {
                Toast.makeText(this@SeriesVideoPlayerActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun play(id: String) {
        youtube_series_player_view.visibility = View.VISIBLE

        lifecycle.addObserver(youtube_series_player_view);
        youtube_series_player_view.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(id, 0F)


            }
        })
    }

    private fun getSimilarVideos(tvId: Int?) {
        var call = ApiClient().service?.getYoutubeSimilarSeries(tvId)
        call?.enqueue(object : Callback<SeriesResponse> {
            override fun onResponse(
                call: Call<SeriesResponse>,
                response: Response<SeriesResponse>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.results
                    similarSeries.setData(list!! as List<SeriesResult>)
                } else {
                    Toast.makeText(
                        this@SeriesVideoPlayerActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                Toast.makeText(this@SeriesVideoPlayerActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}