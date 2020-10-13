package com.davidm.satisbeer.featurehome.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.davidm.satisbeer.featurehome.databinding.ActivityHomeBinding
import com.davidm.satisbeer.uicomponents.CustomDividerDecoration
import dagger.android.AndroidInjection
import kotlinx.coroutines.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    private val mainScope = MainScope()

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

        binding.searchBar.addTextChangedListener(
            getDebouncedTextWatcher { homeViewModel.searchForBeer(it) }
        )
    }

    private fun getDebouncedTextWatcher(onTextChanged: ((String) -> Unit)): TextWatcher {
        return object : TextWatcher {
            var searchedText = ""

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                val trimmed = text.trim().toString()
                searchedText = trimmed
                mainScope.launch {
                    delay(300)
                    if (searchedText != trimmed) {
                        return@launch // text changed while we were waiting
                    }

                    onTextChanged(searchedText)
                }
            }

            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        }
    }

    override fun onDestroy() {
        mainScope.cancel()
        super.onDestroy()
    }
}