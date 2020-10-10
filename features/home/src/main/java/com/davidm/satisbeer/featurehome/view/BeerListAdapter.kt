package com.davidm.satisbeer.featurehome.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidm.satisbeer.featurehome.R
import com.davidm.satisbeer.featurehome.data.Beer
import com.squareup.picasso.Picasso

class BeerListAdapter : PagedListAdapter<Beer, RecyclerView.ViewHolder>(
    DiffUtilCallBack()
) {

    private val WEEKEND_OFFER_TYPE = 0
    private val LIST_ITEM_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var view: View

        return if (viewType == WEEKEND_OFFER_TYPE) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_weekend_offers, parent, false);
            WeekendOfferViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_beer_list, parent, false)
            ListItemViewHolder(view)
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == WEEKEND_OFFER_TYPE) {
//            (viewHolder as WeekendOfferViewHolder).title.text =
//                Resources.getSystem().getText(R.string.weekend_offers_title_text)
//
//            viewHolder.subTitle.text =
//                Resources.getSystem().getText(R.string.weekend_offer_subtitle_text)

        } else {

            getItem(position).let {
                (viewHolder as ListItemViewHolder).beerName.text = it?.name
                viewHolder.beerSubtitle.text = it?.tagLine
                viewHolder.beerDescription.text = it?.description
                Picasso.get().load(it?.imageUrl).into(viewHolder.beerImage)
            }
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

class ListItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val beerName: TextView = view.beerTitle
    val beerSubtitle: TextView = view.beerSubtitle
    val beerDescription: TextView = view.beerDescription
    val beerImage: ImageView = view.beerImage
}

class WeekendOfferViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.weekendOffersTitle
    val subTitle: TextView = view.weekendOffersSubtitle
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