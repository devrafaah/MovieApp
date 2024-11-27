package com.example.movieapp.presenter.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSpashBinding
import com.example.movieapp.util.navigateWithAnimations


class SpashFragment : Fragment() {

    private var _binding: FragmentSpashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpashBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSplashScreen()
    }

    private fun initSplashScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                findNavController().navigateWithAnimations(R.id.action_spashFragment_to_onboardingFragment)
            }
        }, 3000)
    }


}