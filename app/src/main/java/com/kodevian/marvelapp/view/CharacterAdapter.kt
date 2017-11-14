package com.kodevian.marvelapp.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kodevian.marvelapp.R
import com.kodevian.marvelapp.model.CharacterEntity



class CharacterAdapter(var items: MutableList<CharacterEntity>, val listener: (CharacterEntity) -> Unit) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            name.text = items[position].name
            Glide.with(img.context)
                    .load(items[position].thumbnail.getFullImage())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logomarvel)
                    .error(R.drawable.logomarvel)
                    .into(holder.img)
            holder.itemView.setOnClickListener {
                listener(items[position])
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_character,parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addCharacter(character:CharacterEntity){
        items.add(character)
        notifyItemInserted(items.size-1)

    }

    fun clear(){
        items.clear()
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var img: ImageView = view.findViewById(R.id.img_cover)
        var name : TextView = view.findViewById(R.id.tv_name)
    }
}
