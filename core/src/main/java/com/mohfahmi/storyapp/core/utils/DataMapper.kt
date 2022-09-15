package com.mohfahmi.storyapp.core.utils

import com.mohfahmi.storyapp.core.data.source.remote.models.LoginResponse
import com.mohfahmi.storyapp.core.data.source.remote.models.RegisterResponse
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.models.Register

fun LoginResponse?.mapToDomain(): Login {
    return Login(
        this?.loginResult?.userId ?: "",
        this?.loginResult?.name ?: "",
        this?.loginResult?.token ?: ""
    )
}

fun RegisterResponse?.mapToDomain(): Register {
    return Register(this?.error ?: false, this?.message ?: "")
}