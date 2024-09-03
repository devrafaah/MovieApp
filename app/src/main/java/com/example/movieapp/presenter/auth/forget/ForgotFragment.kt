package com.example.movieapp.presenter.auth.forget

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
import com.example.movieapp.databinding.FragmentForgotBinding
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.movieapp.presenter.auth.login.LoginViewModel
import com.example.movieapp.util.StateView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgotFragment : Fragment() {

    private var _binding: FragmentForgotBinding? = null

    private val viewmodel: ForgotViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

    }

    private fun initListener() {
        binding.forgotBtn.setOnClickListener{
            validateData()
        }
        Glide
            .with(this)
            .load(R.drawable.loading)
            .into(binding.progressBarLoading);
    }

    private fun validateData() {
        val email = binding.forgotEmail.text.toString()

        if(email.isNotEmpty()) {
            forgotPassword(email)
        }else {

        }
    }
    private fun forgotPassword(email: String) {
        viewmodel.forgot(email).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {
                    binding.progressBarLoading.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBarLoading.isVisible = false
                    Toast.makeText(requireContext(), "Email enviado com sucesso!", Toast.LENGTH_SHORT).show()

                }
                is StateView.Error -> {
                    binding.progressBarLoading.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}