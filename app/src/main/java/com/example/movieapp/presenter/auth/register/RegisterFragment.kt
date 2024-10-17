package com.example.movieapp.presenter.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentRegisterBinding
import com.example.movieapp.util.FirebaseHelper
import com.example.movieapp.util.StateView
import com.example.movieapp.util.hideKeyboard
import com.example.movieapp.util.initToolbar
import com.example.movieapp.util.isEmailValid
import com.example.movieapp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    private val viewmodel: RegisterViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        initListener()
    }

    private fun initListener() {
        binding.registerBtn.setOnClickListener{
            validateData()
        }
        Glide
            .with(this)
            .load(R.drawable.loading)
            .into(binding.progressBarLoading);
    }

    private fun validateData() {
        val email = binding.registerEmail.text.toString()
        val password = binding.registerPassword.text.toString()

        if(email.isEmailValid()) {
            if(password.isNotEmpty()){
                hideKeyboard()
                registerUser(email, password)
            }else{
                showSnackBar(
                    R.string.text_password_empty
                )
            }
        }else {
            showSnackBar(
                R.string.text_email_empty_invalid
            )
        }
    }

    private fun registerUser(email: String, password: String) {
        viewmodel.register(email, password).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.progressBarLoading.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBarLoading.isVisible = false
                    showSnackBar(
                        R.string.text_register_sucess_register_fragment
                    )
                }
                is StateView.Error -> {
                    binding.progressBarLoading.isVisible = false
                    showSnackBar(
                        FirebaseHelper.validError(stateView.message?: "")
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}