package me.arwan.weatherforecast.core

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


fun ViewModel.launchSafeIO(
    blockBefore: suspend CoroutineScope.() -> Unit = {},
    blockAfter: suspend CoroutineScope.(Boolean) -> Unit = {},
    blockException: suspend CoroutineScope.(Exception) -> Unit = {},
    blockIO: suspend CoroutineScope.() -> Unit,
): Job {
    var isForCancelled = false
    return viewModelScope.launch {
        try {
            blockBefore()
            withContext(Dispatchers.IO) { blockIO() }
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> {
                    isForCancelled = true
                    throw e
                }

                else -> blockException(e)
            }
        } finally {
            blockAfter(isForCancelled)
        }
    }
}

fun Context?.showToast(message: String) {
    if (this == null) return
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View?.setGone() {
    this?.visibility = View.GONE
}

fun View?.setVisible() {
    this?.visibility = View.VISIBLE
}

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        view.clearFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }
}

fun Activity.showKeyboard(editText: EditText) {
    if (editText.requestFocus()) {
        val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun Activity.clearFocusOnTouchOutside(event: MotionEvent) {
    if (event.action == MotionEvent.ACTION_DOWN) {
        val v = currentFocus
        if (v is EditText) {
            val outRect = Rect()
            v.getGlobalVisibleRect(outRect)
            if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                hideKeyboard()
            }
        }
    }
}