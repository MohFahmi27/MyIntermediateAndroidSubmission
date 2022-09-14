package com.mohfahmi.storyapp.core.data.source.remote.models

import com.squareup.moshi.Json

data class PostStoryResponse(

	@Json(name="error")
	val error: Boolean? = null,

	@Json(name="message")
	val message: String? = null
)
