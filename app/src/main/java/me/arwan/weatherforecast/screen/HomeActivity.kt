package me.arwan.weatherforecast.screen

import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import me.arwan.weatherforecast.core.clearFocusOnTouchOutside
import me.arwan.weatherforecast.core.hideKeyboard
import me.arwan.weatherforecast.core.showKeyboard
import me.arwan.weatherforecast.databinding.ActivityHomeBinding


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSearchEditText()
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