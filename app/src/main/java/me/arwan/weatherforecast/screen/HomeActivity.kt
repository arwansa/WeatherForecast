package me.arwan.weatherforecast.screen

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


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSearchEditText()
        observeFavoriteLocations()
        viewModel.loadFavoriteLocation()
        viewModel.save()
    }

    private fun observeFavoriteLocations() = lifecycleScope.launch {
        viewModel.favoriteLocationResult.collect {
            val result = viewModel.favoriteLocationResult.value
            when (result.status) {
                Resource.Status.IDLE -> {}
                Resource.Status.LOADING -> showToast("Loading")
                Resource.Status.SUCCESS -> showToast("Success ${result.data?.size}")
                Resource.Status.ERROR -> showToast("Error ${result.message.orEmpty()}")
            }
        }
    }

    private fun setupSearchEditText() = with(binding.searchEditText) {
        showKeyboard(this)
        setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideKeyboard()
                    true
                }

                else -> false
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let { clearFocusOnTouchOutside(it) }
        return super.dispatchTouchEvent(ev)
    }
}