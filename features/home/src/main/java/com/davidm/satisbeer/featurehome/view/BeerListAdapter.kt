package com.davidm.satisbeer.featurehome.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.databinding.ItemBeerListBinding
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso

class BeerListAdapter : PagingDataAdapter<Beer, ListItemViewHolder>(diffUtilCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {

        val binding =
            ItemBeerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ListItemViewHolder, position: Int) {
        viewHolder.bind(getItem(position) ?: return)
    }

    companion object {
        private val diffUtilCallBack = DiffUtilCallBack()
    }
}

private fun showBottomSheet(context: Context, item: Beer) {
    val beerDetailsFragment = BeerDetailsFragment()

    val bundle = Bundle()
    bundle.putParcelable("key", item)
    beerDetailsFragment.arguments = bundle

    beerDetailsFragment.show(
        (context as AppCompatActivity).supportFragmentManager,
        beerDetailsFragment.tag
    )
}

class ListItemViewHolder(view: ItemBeerListBinding) : RecyclerView.ViewHolder(view.root) {
    private val beerName: MaterialTextView = view.beerTitle
    private val beerSubtitle: MaterialTextView = view.beerSubtitle
    private val beerDescription: MaterialTextView = view.beerDescription
    private val beerImage: ImageView = view.beerImage
    private val beerButton: MaterialTextView = view.beerMoreInfoButton

    fun bind(beer: Beer) {
        beerName.text = beer.name
        beerSubtitle.text = beer.tagLine
        beerDescription.text = beer.description
        Picasso.get().load(beer.imageUrl).into(beerImage)
        beerButton.setOnClickListener { showBottomSheet(this.itemView.context, beer) }
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