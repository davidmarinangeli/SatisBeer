package com.davidm.satisbeer.featurehome.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.databinding.ItemBeerListBinding
import com.squareup.picasso.Picasso

class BeerListAdapter : PagedListAdapter<Beer, RecyclerView.ViewHolder>(diffUtilCallBack) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemBeerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val item = getItem(position) ?: return
        (viewHolder as ListItemViewHolder).bind(item)
    }

    companion object {
        private val diffUtilCallBack = DiffUtilCallBack()
    }
}

class ListItemViewHolder(view: ItemBeerListBinding) : RecyclerView.ViewHolder(view.root) {
    private val beerName: TextView = view.beerTitle
    private val beerSubtitle: TextView = view.beerSubtitle
    private val beerDescription: TextView = view.beerDescription
    private val beerImage: ImageView = view.beerImage

    fun bind(beer: Beer) {
        beerName.text = beer.name
        beerSubtitle.text = beer.tagLine
        beerDescription.text = beer.description
        Picasso.get().load(beer.imageUrl).into(beerImage)
    }
}


class DiffUtilCallBack : DiffUtil.ItemCallback<Beer>() {

    override fun areItemsTheSame(
        oldItem: Beer,
        newItem: Beer
    ): Boolean {
        Log.d("BeerListAdapter", "areItemsTheSame ${oldItem.id == newItem.id}")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Beer,
        newItem: Beer
    ): Boolean {
        return oldItem.name == newItem.name
                && oldItem.description == newItem.description
    }
}