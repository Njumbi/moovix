package com.example.moovix.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.model.MovieModel
import com.example.moovix.data.model.VideoResult
import com.example.moovix.ui.MoviePlayerActivity
import kotlinx.android.synthetic.main.item_now_playing.*
import kotlinx.android.synthetic.main.item_now_playing.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MoviesVH>() {

    private var data = arrayListOf<MovieModel>()


    fun setData(list: List<MovieModel>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesVH {
        return MoviesVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_now_playing, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MoviesVH, position: Int) {
        holder.itemView.tv_title.text = data[position].title
        holder.itemView.tv_date.text = data[position].releaseDate
        holder.itemView.iv_movie.setOnClickListener {
            val intent = Intent(holder.itemView.context, MoviePlayerActivity::class.java)
            intent.putExtra("data", data[position])
            holder.itemView.context.startActivity(intent)
        }
        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + data[position].posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(holder.itemView.iv_movie);
    }

    override fun getItemCount(): Int = data.size

    class MoviesVH(private val view: View) : RecyclerView.ViewHolder(view)
}