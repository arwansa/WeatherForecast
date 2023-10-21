package me.arwan.weatherforecast.core

import android.content.Context
import android.view.View
import android.widget.Toast
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