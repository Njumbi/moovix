package com.example.moovix.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.model.SeriesResponse
import com.example.moovix.data.model.SeriesResult
import com.example.moovix.ui.SeriesVideoPlayerActivity
import kotlinx.android.synthetic.main.item_series_airing.view.*
import kotlinx.android.synthetic.main.item_upcoming_movie.view.*

class AiringSeriesAdapter : RecyclerView.Adapter<AiringSeriesAdapter.AiringSeriesVh>() {
    private var data = arrayListOf<SeriesResult>()

    fun setData(list: List<SeriesResult>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    class AiringSeriesVh(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AiringSeriesVh {
        return AiringSeriesVh(
            LayoutInflater.from(parent.context).inflate(R.layout.item_series_airing, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AiringSeriesVh, position: Int) {
        holder.itemView.tv_airing_series_date.text = data[position].name
        holder.itemView.tv_airing_series_date.text = data[position].firstAirDate

         holder.itemView.iv_series_airing.setOnClickListener {
            val intent = Intent(holder.itemView.context, SeriesVideoPlayerActivity::class.java)
            intent.putExtra("seriesData",data[position])
            holder.itemView.context.startActivity(intent)
        }

        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + data[position].posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(holder.itemView.iv_series_airing)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}