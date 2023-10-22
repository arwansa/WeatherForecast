package me.arwan.weatherforecast.screen.home

import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.arwan.weatherforecast.core.Resource
import me.arwan.weatherforecast.core.clearFocusOnTouchOutside
import me.arwan.weatherforecast.core.hideKeyboard
import me.arwan.weatherforecast.core.showKeyboard
import me.arwan.weatherforecast.core.showToast
import me.arwan.weatherforecast.databinding.ActivityHomeBinding
import me.arwan.weatherforecast.domain.model.coordinates.CoordinatesDto

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var binding: ActivityHomeBinding
    private lateinit var searchCityAdapter: CityAdapter
    private lateinit var favoriteCoordinateAdapter: CityAdapter

    private val cityClickedListener by lazy {
        object : ItemClickListener {
            override fun onItemClicked(coordinate: CoordinatesDto) {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchEditText()
        setupSearchCityList()
        setupFavoriteCoordinateList()

        observeFavoriteLocations()
        observeSearchCity()
    }

    private fun setupSearchEditText() = with(binding.searchEditText) {
        showKeyboard(this)
        setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideKeyboard()
                    viewModel.getCoordinatesByLocationName(
                        binding.searchEditText.text.toString().trim()
                    )
                    true
                }

                else -> false
            }
        }
    }

    private fun setupSearchCityList() {
        searchCityAdapter = CityAdapter(cityClickedListener)
        binding.searchResultsList.adapter = searchCityAdapter
    }

    private fun setupFavoriteCoordinateList() {
        favoriteCoordinateAdapter = CityAdapter(cityClickedListener)
        binding.favoriteLocationsList.adapter = favoriteCoordinateAdapter
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { clearFocusOnTouchOutside(it) }
        return super.dispatchTouchEvent(ev)
    }

    private fun observeSearchCity() = lifecycleScope.launch {
        viewModel.coordinatesResult.collect {
            val result = viewModel.coordinatesResult.value
            when (result.status) {
                Resource.Status.IDLE -> {}
                Resource.Status.LOADING -> showToast("Loading")
                Resource.Status.SUCCESS -> {
                    result.data?.let {
                        searchCityAdapter.setData(it)
                    }
                }

                Resource.Status.ERROR -> showToast("Error ${result.message.orEmpty()}")
            }
        }
    }

    private fun observeFavoriteLocations() = lifecycleScope.launch {
        viewModel.favoriteLocationResult.collect {
            val result = viewModel.favoriteLocationResult.value
            when (result.status) {
                Resource.Status.IDLE -> {}
                Resource.Status.LOADING -> showToast("Loading")
                Resource.Status.SUCCESS -> {
                    result.data?.let {
                        favoriteCoordinateAdapter.setData(it)
                    }
                }

                Resource.Status.ERROR -> showToast("Error ${result.message.orEmpty()}")
            }
        }
    }
}