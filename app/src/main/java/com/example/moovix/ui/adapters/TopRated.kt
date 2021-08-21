package com.example.moovix.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.model.MovieModel
import com.example.moovix.data.model.Result
import com.example.moovix.data.model.TopRatedMoviesResponse
import com.example.moovix.ui.MoviePlayerActivity
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.item_top_rated_movies.view.*


class TopRated : RecyclerView.Adapter<TopRated.TopRatedVH>(){
    private var data = arrayListOf<MovieModel>()

    fun setData(list: List<MovieModel>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    class TopRatedVH(view : View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedVH {
        return TopRatedVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_top_rated_movies,parent,false)
        )
    }

    override fun onBindViewHolder(holder: TopRatedVH, position: Int) {
        holder.itemView.tv_tr_title.text = data[position].title
        holder.itemView.tv_tr_date.text =data[position].releaseDate

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MoviePlayerActivity::class.java)
            intent.putExtra("data", data[position])
            holder.itemView.context.startActivity(intent)
        }

        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + data[position].posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(holder.itemView.iv_top_rated);
    }

    override fun getItemCount(): Int {
        return data.size
    }

}