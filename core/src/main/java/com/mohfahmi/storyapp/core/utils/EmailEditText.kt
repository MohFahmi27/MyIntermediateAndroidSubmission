package com.mohfahmi.storyapp.core.utils

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.mohfahmi.storyapp.core.R

class EmailEditText : TextInputEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        init()
    }

    private fun isEmailHaveValidFormat(email: CharSequence): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches() && email.isNotBlank()
    }

    private fun showErrorMessage() {
        error = context.getString(R.string.invalid_email)
    }

    private fun clearErrorMessage() {
        error = null
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (isEmailHaveValidFormat(text)) clearErrorMessage()
                else showErrorMessage()
            }

            override fun afterTextChanged(p0: Editable?) = Unit
        })
    }
}