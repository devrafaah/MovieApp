package com.example.movieapp.presenter.main.bottomBar.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.MovieGenreFragmentDirections
import com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.adapter.MovieLargeAdapter
import com.example.movieapp.util.StateView
import com.example.movieapp.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: SearhViewModel by viewModels()
    private lateinit var movieAdapter: MovieLargeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.progressBar.isVisible = false
        initRecycler()
        initSearchView()

        initObserver()
    }

    private fun initObserver() {
        stateObserver()
        getObserverBySearch()
    }

    private fun initRecycler() {
        movieAdapter = MovieLargeAdapter(
            movieClickListener = { movieId ->
                movieId?.let {
                    val action = MovieGenreFragmentDirections.actionGlobalMovieDetailsFragment(movieId)
                    findNavController().navigate(action)
                }
            }
        )
        with(binding.rvMovie) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }
    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewmodel.getMoviesGenresBySearch(query)
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }
    private fun stateObserver() {
        viewmodel.searchState.observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.rvMovie.isVisible = false
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    binding.rvMovie.isVisible = true
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun getObserverBySearch() {
        viewmodel.movieList.observe(viewLifecycleOwner) { moviesList ->
            binding.layoutEmpty.isVisible = moviesList.isEmpty()
            binding.rvMovie.isVisible = moviesList.isNotEmpty()
            movieAdapter.submitList(moviesList)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}