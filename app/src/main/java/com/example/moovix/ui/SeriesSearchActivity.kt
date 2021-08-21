package com.example.moovix.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moovix.R
import com.example.moovix.data.ApiClient
import com.example.moovix.data.model.MovieSearchResponse
import com.example.moovix.data.model.MoviesSearchResult
import com.example.moovix.ui.adapters.MoviesSearchAdapter
import kotlinx.android.synthetic.main.activity_movies_search.*
import kotlinx.android.synthetic.main.activity_series_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeriesSearchActivity : AppCompatActivity() {
    private lateinit var seriesAdapter: MoviesSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_search)

        seriesAdapter = MoviesSearchAdapter()

        rv_series_search.layoutManager = GridLayoutManager(this, 3)
        rv_series_search.adapter = seriesAdapter

        setSupportActionBar(toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""

        et_search_series.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        et_search_series.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (it.length > 3) {
                        search(it.toString())
                    } else {
                        seriesAdapter.clear()
                    }
                } ?: kotlin.run {
                    seriesAdapter.clear()
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

                if (response.isSuccessful) {
                    val list = response.body()?.results
                    if (list.isNullOrEmpty()) {
                        tv_series_search_no_data.visibility = View.VISIBLE
                    } else {
                        tv_series_search_no_data.visibility = View.GONE
                        seriesAdapter.setData(list as List<MoviesSearchResult>)
                    }


                } else {
                    Toast.makeText(
                        this@SeriesSearchActivity,
                        response.errorBody().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<MovieSearchResponse>, t: Throwable) {
                Toast.makeText(this@SeriesSearchActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}