package com.example.moovix.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.model.MovieModel
import com.example.moovix.ui.MoviePlayerActivity
import kotlinx.android.synthetic.main.item_now_playing.view.*
import kotlinx.android.synthetic.main.item_similar_movies.view.*

class SimilarMoviesAdapter:RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMoviesVh>() {
    private var data = arrayListOf<MovieModel>()

    fun setData(list: List<MovieModel>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }


    class SimilarMoviesVh(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMoviesVh {
   return SimilarMoviesVh(
       LayoutInflater.from(parent.context).inflate(R.layout.item_similar_movies, parent, false)
   )
    }

    override fun onBindViewHolder(holder: SimilarMoviesVh, position: Int) {
        holder.itemView.tv_movie_similar_item_title.text = data[position].title
        holder.itemView.tv_movie_similar_item_desc.text = data[position].overview
        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + data[position].posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(holder.itemView.iv_movie_similar_item);
    }

    override fun getItemCount(): Int {
       return data.size
    }
}