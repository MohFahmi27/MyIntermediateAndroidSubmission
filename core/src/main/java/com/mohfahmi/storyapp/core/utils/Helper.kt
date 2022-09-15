package com.mohfahmi.storyapp.core.utils

import org.json.JSONObject
import retrofit2.Response

fun Response<out Any>.getErrorMessage(): String {
    val errorBody = JSONObject(this.errorBody()?.charStream()?.readText() ?: "")
    return errorBody.get("message") as String
}