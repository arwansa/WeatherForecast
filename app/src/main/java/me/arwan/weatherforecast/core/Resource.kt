package me.arwan.weatherforecast.core

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        IDLE,
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {

        fun <T> idle(data: T? = null): Resource<T> {
            return Resource(Status.IDLE, data, null)
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
