package com.example.moovix.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.model.SeriesResult
import com.example.moovix.ui.SeriesVideoPlayerActivity
import kotlinx.android.synthetic.main.item_series_top_rated.view.*


class TopRatedSeries : RecyclerView.Adapter<TopRatedSeries.TopRatedSeriesVh> (){

    private var data = arrayListOf<SeriesResult>()

    fun setData(list : List<SeriesResult>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    class TopRatedSeriesVh(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedSeriesVh {
      return TopRatedSeriesVh(
          LayoutInflater.from(parent.context).inflate(R.layout.item_series_top_rated,parent,false)
      )
    }

    override fun onBindViewHolder(holder: TopRatedSeriesVh, position: Int) {
        holder.itemView.tv_series_tr_title.text = data[position].name
        holder.itemView.tv_series_tr_date.text = data[position].firstAirDate
        holder.itemView.iv_series_top_rated.setOnClickListener {
            val intent = Intent(holder.itemView.context,SeriesVideoPlayerActivity::class.java)
            intent.putExtra("seriesData",data[position])
            holder.itemView.context.startActivity(intent)
        }

        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + data[position].posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(holder.itemView.iv_series_top_rated)


    }

    override fun getItemCount(): Int {
      return data.size
    }


}
