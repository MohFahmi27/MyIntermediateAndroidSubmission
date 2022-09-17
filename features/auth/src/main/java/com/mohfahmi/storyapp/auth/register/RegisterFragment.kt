package com.mohfahmi.storyapp.auth.register

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mohfahmi.storyapp.auth.R
import com.mohfahmi.storyapp.auth.databinding.FragmentRegisterBinding
import com.mohfahmi.storyapp.auth.register.RegisterFragmentDirections.Companion.actionRegisterFragmentToLoadingDialogFragment
import com.mohfahmi.storyapp.auth.register.RegisterFragmentDirections.Companion.actionRegisterFragmentToLoginFragment
import com.mohfahmi.storyapp.core.R.string.register_successfully
import com.mohfahmi.storyapp.core.common_ui.ErrorBottomSheetDialogFragment
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.utils.Status
import com.mohfahmi.storyapp.core.utils.UiState
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding: FragmentRegisterBinding by viewBinding()
    private val viewModel: RegisterViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnRegister.setOnClickListener {
                fetchRegister(
                    edRegisterName.text.toString(),
                    edRegisterEmail.text.toString(),
                    edRegisterPassword.text.toString()
                )
            }
        }
    }

    private fun fetchRegister(name: String, email: String, password: String) {
        viewModel.register(name, email, password)
            .observe(viewLifecycleOwner, ::manageRegisterResponse)
    }

    private fun manageRegisterResponse(uiState: UiState<Register>) {
        when (uiState.status) {
            Status.LOADING -> {
                findNavController().navigate(actionRegisterFragmentToLoadingDialogFragment())
            }
            Status.HIDE_LOADING -> {
                findNavController().navigateUp()
            }
            Status.SUCCESS -> {
                FancyToast.makeText(
                    requireContext(),
                    getString(register_successfully),
                    FancyToast.LENGTH_LONG,
                    FancyToast.SUCCESS,
                    false
                ).show()
                findNavController().navigate(
                    actionRegisterFragmentToLoginFragment(
                        binding.edRegisterEmail.text.toString(),
                        binding.edRegisterPassword.text.toString()
                    )
                )
            }
            Status.ERROR -> {
                ErrorBottomSheetDialogFragment(
                    uiState.message
                        ?: getString(com.mohfahmi.storyapp.core.R.string.something_went_wrong)
                ).show(parentFragmentManager, ErrorBottomSheetDialogFragment.TAG)
            }
        }
    }

}