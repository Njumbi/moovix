package com.example.moovix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.model.MoviesSearchResult
import kotlinx.android.synthetic.main.item_movies_search.view.*

class MoviesSearchAdapter : RecyclerView.Adapter<MoviesSearchAdapter.MoviesSearchAdapterVh>() {
    private var data = arrayListOf<MoviesSearchResult>()

    fun setData(list: List<MoviesSearchResult>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()

    }

    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

    class MoviesSearchAdapterVh(view: View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesSearchAdapterVh {
    return MoviesSearchAdapterVh(
        LayoutInflater.from(parent.context).inflate(R.layout.item_movies_search,parent,false)
    )
    }

    override fun onBindViewHolder(holder: MoviesSearchAdapterVh, position: Int) {
    holder.itemView.tv_movies_search.text = data[position].title
        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + data[position].posterPath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(holder.itemView.iv_movies_search);
    }

    override fun getItemCount(): Int {
     return data.size
    }

}