package com.mohfahmi.storyapp.core.utils

import android.view.View
import org.json.JSONObject
import retrofit2.Response

fun Response<out Any>.getErrorMessage(): String {
    val errorBody = JSONObject(this.errorBody()?.charStream()?.readText() ?: "")
    return errorBody.get("message") as String
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}