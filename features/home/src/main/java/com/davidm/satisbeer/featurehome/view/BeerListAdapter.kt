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
import com.davidm.satisbeer.featurehome.databinding.ItemWeekendOffersBinding
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso

class BeerListAdapter : PagingDataAdapter<Beer, RecyclerView.ViewHolder>(diffUtilCallBack) {

    private val WEEKEND_OFFER_TYPE = 0
    private val LIST_ITEM_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            WEEKEND_OFFER_TYPE -> {
                val binding =
                    ItemWeekendOffersBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                WeekendOfferViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemBeerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ListItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == LIST_ITEM_TYPE) {
            (viewHolder as ListItemViewHolder).bind(getItem(position) ?: return)
        }
    }

    companion object {
        private val diffUtilCallBack = DiffUtilCallBack()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> WEEKEND_OFFER_TYPE
            else -> LIST_ITEM_TYPE
        }
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

class WeekendOfferViewHolder(view: ItemWeekendOffersBinding) : RecyclerView.ViewHolder(view.root)

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