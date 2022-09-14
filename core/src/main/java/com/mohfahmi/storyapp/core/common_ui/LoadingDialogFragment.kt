package com.mohfahmi.storyapp.core.common_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.dialogfragment.viewBinding
import androidx.fragment.app.DialogFragment
import com.mohfahmi.storyapp.core.databinding.DialogFragmentLoadingBinding

class LoadingDialogFragment : DialogFragment() {

    private val binding: DialogFragmentLoadingBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        requireDialog().window?.setBackgroundDrawableResource(android.R.color.transparent)
        isCancelable = false
        return binding.root
    }
}