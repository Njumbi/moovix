package com.example.moovix.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moovix.R
import com.example.moovix.data.ApiClient
import com.example.moovix.data.model.MovieModel
import com.example.moovix.data.model.MoviesResponse
import com.example.moovix.data.model.Result
import com.example.moovix.data.model.TopRatedMoviesResponse
import com.example.moovix.ui.MoviePlayerActivity
import com.example.moovix.ui.MoviesSearchActivity
import com.example.moovix.ui.adapters.MovieAdapter
import com.example.moovix.ui.adapters.TopRated
import com.example.moovix.ui.adapters.UpComingAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.item_now_playing.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesFragment : Fragment() {

    private lateinit var adapter: MovieAdapter
    private lateinit var trAdapter: TopRated
    private lateinit var upComingAdapter: UpComingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MovieAdapter()
        trAdapter = TopRated()
        upComingAdapter = UpComingAdapter()


        rv_movies_now_playing.layoutManager = LinearLayoutManager(
            requireActivity(),
            RecyclerView.HORIZONTAL,
            false
        )
        rv_movies_now_playing.adapter = adapter

        rv_movies_top_rated.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        rv_movies_top_rated.adapter = trAdapter

        rv_movies_upcoming.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        rv_movies_upcoming.adapter = upComingAdapter

        progressBar.visibility = View.VISIBLE
        ll_movies_holder.visibility = View.GONE
        ll_movies_top_rated_holder.visibility = View.GONE
        ll_movies_upcoming_holder.visibility = View.GONE

        fetchData()
        fetchTopRated()
        fetchUpcoming()

        et_search_movies.setOnClickListener {
            val intent = Intent(activity, MoviesSearchActivity::class.java)
            startActivity(intent)
        }

    }


    private fun fetchData() {
        val call = ApiClient().service?.getNowPlayingMovies()
        call?.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {

                if (response.isSuccessful) {

                    val list = response.body()?.results
                    adapter.setData(list!!)

                    progressBar.visibility = View.GONE
                    ll_movies_holder.visibility = View.VISIBLE

                } else {
                    Toast.makeText(
                        requireActivity(),
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun fetchTopRated() {
        val call = ApiClient()?.service?.getTopRated()
        call?.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.results
                    trAdapter.setData(list as List<MovieModel>)
                    progressBar.visibility = View.GONE
                    ll_movies_top_rated_holder.visibility = View.VISIBLE
                } else {
                    Toast.makeText(
                        requireActivity(),
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun fetchUpcoming() {
        val call = ApiClient()?.service?.getUpcomingMovies()
        call?.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val list = response.body()?.results

                    upComingAdapter.setData(list as List<MovieModel>)
                    ll_movies_upcoming_holder.visibility = View.VISIBLE

                } else {
                    Toast.makeText(
                        requireActivity(),
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}