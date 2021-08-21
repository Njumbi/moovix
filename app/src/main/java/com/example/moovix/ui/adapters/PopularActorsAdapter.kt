package com.example.moovix.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moovix.R
import com.example.moovix.data.model.PopularActors
import kotlinx.android.synthetic.main.item_pop_actors.view.*

class PopularActorsAdapter : RecyclerView.Adapter<PopularActorsAdapter.PopularActorsVh> (){
    private  var data = arrayListOf<PopularActors>()

    fun setData(list: List<PopularActors>){
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    class PopularActorsVh(view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularActorsVh {
      return PopularActorsVh(
          LayoutInflater.from(parent.context).inflate(R.layout.item_pop_actors,parent,false)
      )
    }

    override fun onBindViewHolder(holder: PopularActorsVh, position: Int) {
     holder.itemView.tv_pop_actors_name.text =data[position].name

        Glide
            .with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + data[position].profilePath)
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .into(holder.itemView.iv_pop_actors);
    }

    override fun getItemCount(): Int {
       return data.size
    }
}