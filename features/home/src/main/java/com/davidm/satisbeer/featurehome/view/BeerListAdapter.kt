package com.davidm.satisbeer.featurehome.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.databinding.ItemBeerListBinding
import com.davidm.satisbeer.featurehome.databinding.ItemWeekendOffersBinding
import com.squareup.picasso.Picasso

class BeerListAdapter : PagedListAdapter<Beer, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    private val WEEKEND_OFFER_TYPE = 0
    private val LIST_ITEM_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == WEEKEND_OFFER_TYPE) {
            val binding = ItemWeekendOffersBinding.inflate(LayoutInflater.from(parent.context))
            WeekendOfferViewHolder(binding)
        } else {
            val binding = ItemBeerListBinding.inflate(LayoutInflater.from(parent.context))
            ListItemViewHolder(binding)
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val item = getItem(position) ?: return


        if (getItemViewType(position) == WEEKEND_OFFER_TYPE) {
            (viewHolder as WeekendOfferViewHolder).bind(item)

        } else {
            (viewHolder as ListItemViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            WEEKEND_OFFER_TYPE
        } else {
            LIST_ITEM_TYPE
        }
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

class WeekendOfferViewHolder(view: ItemWeekendOffersBinding) : RecyclerView.ViewHolder(view.root) {
    val title: TextView = view.weekendOffersTitle
    val subTitle: TextView = view.weekendOffersSubtitle

    fun bind(item: Beer) {
        //            (viewHolder as WeekendOfferViewHolder).title.text =
//                Resources.getSystem().getText(R.string.weekend_offers_title_text)
//
//            viewHolder.subTitle.text =
//                Resources.getSystem().getText(R.string.weekend_offer_subtitle_text)
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<Beer>() {

    override fun areItemsTheSame(
        oldItem: Beer,
        newItem: Beer
    ): Boolean {
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