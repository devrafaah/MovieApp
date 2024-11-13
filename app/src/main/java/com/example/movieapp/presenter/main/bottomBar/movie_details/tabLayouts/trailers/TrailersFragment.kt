package com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.trailers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSimilarBinding
import com.example.movieapp.databinding.FragmentTrailersBinding

class TrailersFragment : Fragment() {


    private var _binding: FragmentTrailersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTrailersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun initListeners() {

    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

}