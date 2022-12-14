package com.mohfahmi.storyapp.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mohfahmi.storyapp.auth.R
import com.mohfahmi.storyapp.auth.databinding.FragmentLoginBinding
import com.mohfahmi.storyapp.auth.login.LoginFragmentDirections.Companion.actionLoginFragmentToLoadingDialogFragment
import com.mohfahmi.storyapp.auth.login.LoginFragmentDirections.Companion.actionLoginFragmentToRegisterFragment
import com.mohfahmi.storyapp.core.R.string.something_went_wrong
import com.mohfahmi.storyapp.core.common_ui.ErrorBottomSheetDialogFragment
import com.mohfahmi.storyapp.core.common_ui.ErrorBottomSheetDialogFragment.Companion.TAG
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.utils.NavigationHelper.HOME_ROUTE
import com.mohfahmi.storyapp.core.utils.Status
import com.mohfahmi.storyapp.core.utils.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModel()
    private val args: LoginFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            if (!args.email.isNullOrEmpty()) {
                edLoginEmail.setText(args.email)
                edLoginPassword.setText(args.password)
            }
            btnLogin.setOnClickListener {
                fetchLogin(edLoginEmail.text.toString(), edLoginPassword.text.toString())
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(actionLoginFragmentToRegisterFragment())
            }
        }
    }

    private fun fetchLogin(email: String, password: String) {
        viewModel.login(email, password).observe(viewLifecycleOwner, ::manageLoginResponse)
    }

    private fun manageLoginResponse(loginState: UiState<Login>) {
        when (loginState.status) {
            Status.LOADING -> {
                findNavController().navigate(actionLoginFragmentToLoadingDialogFragment())
            }
            Status.HIDE_LOADING -> {
                findNavController().navigateUp()
            }
            Status.SUCCESS -> {
                viewModel.loginSuccessful()
                loginState.data?.let { viewModel.saveTokenKey(it.token) }
                startActivity(Intent(requireActivity(), Class.forName(HOME_ROUTE)))
                requireActivity().finish()
            }
            Status.ERROR -> {
                ErrorBottomSheetDialogFragment(
                    loginState.message ?: getString(something_went_wrong)
                ).show(parentFragmentManager, TAG)
            }
        }
    }
}