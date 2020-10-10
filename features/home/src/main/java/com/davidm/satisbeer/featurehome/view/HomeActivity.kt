package com.davidm.satisbeer.featurehome.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.davidm.satisbeer.featurehome.databinding.ActivityHomeBinding
import com.davidm.satisbeer.uicomponents.CustomDividerDecoration
import dagger.android.AndroidInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val recyclerView = binding.beerList
        val homeAdapter = BeerListAdapter()

        recyclerView.addItemDecoration(CustomDividerDecoration(recyclerView.context))
        recyclerView.adapter = homeAdapter

        homeViewModel.getBeerList().observe(this, { homeAdapter.submitList(it) })
    }
}