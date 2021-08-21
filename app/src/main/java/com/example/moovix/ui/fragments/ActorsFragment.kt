package com.example.moovix.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moovix.R
import com.example.moovix.data.ApiClient
import com.example.moovix.data.model.MoviesResponse
import com.example.moovix.data.model.PeopleResponse
import com.example.moovix.data.model.PopularActors
import com.example.moovix.ui.adapters.PopularActorsAdapter
import kotlinx.android.synthetic.main.actors_fragment.*
import kotlinx.android.synthetic.main.fragment_movies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActorsFragment: Fragment() {

    private lateinit var popularActorsAdapter: PopularActorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.actors_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularActorsAdapter = PopularActorsAdapter()
        rv_pop_actors.layoutManager = GridLayoutManager(requireContext(), 3)
        rv_pop_actors.adapter = popularActorsAdapter

        progressBar2.visibility = View.VISIBLE
        ll_pop_actors.visibility =View.GONE

        fetchPopActors()
    }

    private fun fetchPopActors() {
        val call = ApiClient().service?.getPopActors()
        call?.enqueue(object : Callback<PeopleResponse> {
            override fun onResponse(
                call: Call<PeopleResponse>,
                response: Response<PeopleResponse>
            ) {

                if ( response.isSuccessful ){

                    val list = response.body()?.results
                    popularActorsAdapter.setData(list!! as List<PopularActors>)

                    progressBar2.visibility = View.GONE
                    ll_pop_actors.visibility = View.VISIBLE

                }else{
                    Toast.makeText(requireActivity(), response.errorBody().toString(), Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<PeopleResponse>, t: Throwable) {
                Toast.makeText(requireActivity(), t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}