package com.mohfahmi.storyapp.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mohfahmi.storyapp.auth.R
import com.mohfahmi.storyapp.auth.databinding.FragmentLoginBinding
import com.mohfahmi.storyapp.auth.login.LoginFragmentDirections.actionLoginFragmentToRegisterFragment
import com.mohfahmi.storyapp.core.utils.NavigationHelper.HOME_ROUTE

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(requireActivity(), Class.forName(HOME_ROUTE)))
            requireActivity().finish()
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(actionLoginFragmentToRegisterFragment())
        }
    }
}