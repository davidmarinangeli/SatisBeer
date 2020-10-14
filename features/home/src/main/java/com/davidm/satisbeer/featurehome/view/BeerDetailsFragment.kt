package com.davidm.satisbeer.featurehome.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidm.satisbeer.featurehome.data.Beer
import com.davidm.satisbeer.featurehome.data.convertFoodPairingList
import com.davidm.satisbeer.featurehome.databinding.BeerDetailBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class BeerDetailsFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BeerDetailBottomSheetBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = BeerDetailBottomSheetBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val beer = arguments?.getParcelable<Beer>("key")

        binding.beerDetailTitle.text = beer?.name
        binding.beerDetailDescription.text = beer?.description
        binding.beerDetailSubtitle.text = beer?.tagLine
        Picasso.get().load(beer?.imageUrl).into(binding.beerDetailImage)

        if (beer?.foodPairing != null && beer.foodPairing.isNotEmpty()) {
            binding.beerDetailFoodPairingContent.text = convertFoodPairingList(beer.foodPairing)
        } else {
            binding.beerDetailFoodPairing.visibility = View.GONE
            binding.beerDetailFoodPairingContent.visibility = View.GONE
        }
    }
}