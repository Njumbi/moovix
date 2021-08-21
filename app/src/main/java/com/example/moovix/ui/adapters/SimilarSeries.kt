package com.example.moovix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.model.SeriesResult
import kotlinx.android.synthetic.main.item_similar_series.view.*

class SimilarSeries : RecyclerView.Adapter<SimilarSeries.SimilarSeriesVh>() {
    private var data = arrayListOf<SeriesResult>()

    fun setData(list: List<SeriesResult>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    class SimilarSeriesVh(view: View) : RecyclerView.ViewHolder(view) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarSeriesVh {
        return SimilarSeriesVh(
            LayoutInflater.from(parent.context).inflate(R.layout.item_similar_series, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SimilarSeriesVh, position: Int) {
        holder.itemView.tv_series_similar_item_title.text = data[position].name
        holder.itemView.tv_series_similar_item_desc.text = data[position].overview

        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + data[position].posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(holder.itemView.iv_series_similar_item)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}