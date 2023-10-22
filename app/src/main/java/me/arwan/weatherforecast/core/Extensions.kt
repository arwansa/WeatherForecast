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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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

fun Double.kelvinToCelsius(): Int {
    return (this - 273.15).toInt()
}

fun String.toFormattedDateTime(): String? {
    val date = this.toDate()
    val outputDateFormat = SimpleDateFormat("d MMM", Locale.getDefault())
    val outputTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    return date?.let {
        "${outputDateFormat.format(it)}\n${outputTimeFormat.format(it)}"
    }
}

fun String.toDate(): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return format.parse(this)
}

fun Date.startOfDay(): Date {
    return Calendar.getInstance().apply {
        time = this@startOfDay
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.time
}

fun Date.addDays(days: Int): Date {
    return Calendar.getInstance().apply {
        time = this@addDays.startOfDay()
        add(Calendar.DAY_OF_YEAR, days)
    }.time
}

fun String.isDateWithinNextThreeDays(): Boolean {
    val targetDate = this.toDate()?.startOfDay()?.convertToLocalTimeZone()
    val currentDate = Date()
    val startDate = currentDate.addDays(1)
    val endDate = currentDate.addDays(4)

    return targetDate?.after(startDate) == true && targetDate.before(endDate)
}

fun Date.convertToLocalTimeZone(): Date {
    val localZone = TimeZone.getDefault()
    val localOffset = localZone.rawOffset
    val dstOffset = if (localZone.inDaylightTime(this)) localZone.dstSavings else 0

    return Date(this.time + localOffset + dstOffset)
}