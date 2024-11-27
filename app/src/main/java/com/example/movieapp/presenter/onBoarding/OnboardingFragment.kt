package com.example.movieapp.presenter.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentOnboardingBinding
import com.example.movieapp.databinding.FragmentRegisterBinding
import com.example.movieapp.presenter.auth.register.RegisterViewModel
import com.example.movieapp.util.navigateWithAnimations
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.btnStarted.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_onboardingFragment_to_authentication)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}