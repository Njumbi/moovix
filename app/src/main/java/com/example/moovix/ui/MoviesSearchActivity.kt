package com.example.moovix.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moovix.R
import com.example.moovix.data.ApiClient
import com.example.moovix.data.model.MovieSearchResponse
import com.example.moovix.data.model.MoviesSearchResult
import com.example.moovix.data.model.Result
import com.example.moovix.data.model.TopRatedMoviesResponse
import com.example.moovix.ui.adapters.MoviesSearchAdapter
import com.example.moovix.ui.adapters.TopRated
import kotlinx.android.synthetic.main.activity_movies_search.*
import kotlinx.android.synthetic.main.activity_movies_search.et_search_movies
import kotlinx.android.synthetic.main.fragment_movies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesSearchActivity : AppCompatActivity() {
    private lateinit var searchAdapter:MoviesSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_search)

        searchAdapter = MoviesSearchAdapter()

        rv_movies_search.layoutManager = GridLayoutManager(this,3)
        rv_movies_search.adapter = searchAdapter

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""




        et_search_movies.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        et_search_movies.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.length > 3) {
                        search(it.toString())
                    } else {
                        searchAdapter.clear()
                    }
                } ?: kotlin.run {
                    searchAdapter.clear()
                }
            }

        })
    }

    private fun search(query: String) {
        var call = ApiClient().service?.getSearchMovies(query)
        call?.enqueue(object : Callback<MovieSearchResponse> {
            override fun onResponse(
                call: Call<MovieSearchResponse>,
                response: Response<MovieSearchResponse>
            ) {

                if (response.isSuccessful){
                    val list = response.body()?.results
                    if (list.isNullOrEmpty()){
                      tv_movies_no_data.visibility = View.VISIBLE
                    }else{
                        searchAdapter.setData(list as List<MoviesSearchResult>)
                        tv_movies_no_data.visibility = View.GONE
                    }


                }else{
                    Toast.makeText(this@MoviesSearchActivity,response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                Toast.makeText(this@MoviesSearchActivity,t.message,Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}