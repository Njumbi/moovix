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
import com.example.moovix.data.model.SeriesResponse
import com.example.moovix.data.model.SeriesResult
import com.example.moovix.ui.SeriesSearchActivity
import com.example.moovix.ui.adapters.AiringSeriesAdapter
import com.example.moovix.ui.adapters.PopularSeries
import com.example.moovix.ui.adapters.TopRatedSeries
import kotlinx.android.synthetic.main.fragment_series.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeriesFragment : Fragment() {

    private lateinit var adapter: PopularSeries
    private lateinit var srAdapter: TopRatedSeries
    private lateinit var airingSeriesAdapter: AiringSeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PopularSeries()
        srAdapter = TopRatedSeries()
        airingSeriesAdapter = AiringSeriesAdapter()

        rv_series_pop.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        rv_series_pop.adapter = adapter

        rv_series_tr.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
     rv_series_tr.adapter = srAdapter

        rv_series_airing.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
      rv_series_airing.adapter = airingSeriesAdapter

        progressBar1.visibility = View.VISIBLE
        ll_series_pop.visibility = View.GONE
        ll_series_tr.visibility = View.GONE

        et_search_sr.setOnClickListener {
            var intent = Intent(activity,SeriesSearchActivity::class.java)
            startActivity(intent)
        }

        fetchPopSeries()
        fetchTopRatedSeries()
        fetchAiringSeries()

    }

    private fun fetchPopSeries(){
    var call = ApiClient()?.service?.getPopularSeries()
        call?.enqueue(object : Callback<SeriesResponse>{
            override fun onResponse(
                call: Call<SeriesResponse>,
                response: Response<SeriesResponse>
            ) {
                if (response.isSuccessful){
                    val list = response.body()?.results
                    adapter.setData(list!! as List<SeriesResult>)

                    progressBar1.visibility = View.GONE
                    ll_series_pop.visibility = View.VISIBLE

                }else{
                    Toast.makeText(requireActivity(), response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun fetchTopRatedSeries() {
        var call = ApiClient()?.service?.getTopRatedSeries()
        call?.enqueue( object : Callback<SeriesResponse> {
            override fun onResponse(
                call: Call<SeriesResponse>,
                response: Response<SeriesResponse>
            ) {
               if (response.isSuccessful){
                   val list = response.body()?.results
                  srAdapter.setData(list!! as List<SeriesResult>)

                   progressBar1.visibility = View.GONE
                   ll_series_tr.visibility = View.VISIBLE

               }else{
                   Toast.makeText(requireActivity(), response.errorBody().toString(), Toast.LENGTH_SHORT).show()
               }
            }

            override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun fetchAiringSeries(){
        var call = ApiClient()?.service?.getAiringToday()
        call?.enqueue( object : Callback<SeriesResponse> {
            override fun onResponse(
                call: Call<SeriesResponse>,
                response: Response<SeriesResponse>
            ) {
                if (response.isSuccessful){
                    val list = response.body()?.results
                    airingSeriesAdapter.setData(list!! as List<SeriesResult>)

                    progressBar1.visibility = View.GONE
                    ll_series_airing.visibility = View.VISIBLE

                }else{
                    Toast.makeText(requireActivity(), response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }


}