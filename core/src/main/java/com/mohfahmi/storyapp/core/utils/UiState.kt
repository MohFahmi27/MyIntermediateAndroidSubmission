package com.mohfahmi.storyapp.core.utils

data class UiState<out T>(val status: Status, val data: T?, val message: String?) {
    // since this will going to be use a lot, better to use using companion object I suppose
    companion object {
        // this class will help ui to recognize whether api response on loading, error, or success
        fun <T> success(data: T): UiState<T> =
            UiState(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T? = null, message: String): UiState<T> =
            UiState(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data: T? = null): UiState<T> =
            UiState(status = Status.LOADING, data = data, message = null)

        fun <T> hideLoading(data: T? = null): UiState<T> =
            UiState(status = Status.HIDE_LOADING, data = data, message = null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    HIDE_LOADING
}