package com.mohfahmi.storyapp.core.data.source.remote.models

import com.squareup.moshi.Json

data class RegisterResponse(

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)
