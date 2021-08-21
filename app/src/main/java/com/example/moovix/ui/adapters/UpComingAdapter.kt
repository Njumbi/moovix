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
import com.example.moovix.ui.MoviePlayerActivity
import com.example.moovix.ui.SeriesVideoPlayerActivity
import kotlinx.android.synthetic.main.item_upcoming_movie.view.*

class UpComingAdapter: RecyclerView.Adapter<UpComingAdapter.UpcomingAdapterVh>() {
    var data = arrayListOf<MovieModel>()

    fun setData(list: List<MovieModel>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    class UpcomingAdapterVh(view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingAdapterVh {
        return UpcomingAdapterVh(
            LayoutInflater.from(parent.context).inflate(R.layout.item_upcoming_movie,parent,false)
        )
    }

    override fun onBindViewHolder(holder: UpcomingAdapterVh, position: Int) {
       holder.itemView.tv_upcoming_title.text = data[position].title
        holder.itemView.tv_upcoming_date.text = data[position].releaseDate
        holder.itemView.iv_upcoming.setOnClickListener {
            val intent = Intent(holder.itemView.context, MoviePlayerActivity::class.java)
            intent.putExtra("data",data[position])
            holder.itemView.context.startActivity(intent)
        }

        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + data[position].posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(holder.itemView.iv_upcoming)
    }

    override fun getItemCount(): Int {
     return data.size
    }
}