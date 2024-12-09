package com.example.movieapp.presenter.main.bottomBar.home.first_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.MainGraphDirections
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.presenter.main.bottomBar.home.first_screen.adapter.GenreMovieAdapter
import com.example.movieapp.presenter.model.MoviesByGenre
import com.example.movieapp.util.StateView
import com.example.movieapp.util.applyScreenWindowInsets
import com.example.movieapp.util.navigateWithAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var genreMovieAdapter: GenreMovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyScreenWindowInsets(view = view, applyBottom = false)

        initRecycler()
        initObservers()

    }

    private fun initObservers() {
        viewModel.homeState.observe(viewLifecycleOwner) { stateview ->
            when(stateview){
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerGenres.isVisible = false
                }
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerGenres.isVisible = false
                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerGenres.isVisible = true
                }
            }
        }

        viewModel.movieList.observe(viewLifecycleOwner) { moviesByGenre ->
            genreMovieAdapter.submitList(moviesByGenre)
        }
    }

    private fun initRecycler() {
        genreMovieAdapter = GenreMovieAdapter(
            { genreId, name ->
                val action = HomeFragmentDirections.actionMenuHomeToMovieGenreFragment(genreId, name)
                findNavController().navigateWithAnimations(action)

            },
            movieClickListener =  { movieId ->
                movieId?.let {
                    val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                    findNavController().navigateWithAnimations(action)
                }
            }
        )


        with(binding.recyclerGenres) {
            setHasFixedSize(true)
            adapter = genreMovieAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}