package com.example.movieapp.presenter.main.bottomBar.home.first_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.MainGraphDirections
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.presenter.main.bottomBar.home.first_screen.adapter.GenreMovieAdapter
import com.example.movieapp.presenter.model.GenrePresentation
import com.example.movieapp.util.StateView
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


        initRecycler()
        getGenres()

    }

    private fun getGenres() {
        viewModel.getGenres().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    val genres = stateView.data ?: emptyList()
                    genreMovieAdapter.submitList(stateView.data)
                    getMoviesByGenres(genres)
                }
                is StateView.Error -> {

                }
            }
        }
    }

    private fun getMoviesByGenres(genres:List<GenrePresentation>) {
        val genreMutableList = genres.toMutableList()

        genreMutableList.forEachIndexed { index, genre ->
            viewModel.getMoviesGenres(genre.id).observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {

                    }
                    is StateView.Success -> {
                        genreMutableList[index] = genre.copy(
                            movies = stateView.data?.take(5)
                        )
                        lifecycleScope.launch {
                            delay(1000)
                            genreMovieAdapter.submitList(genreMutableList)
                        }
                    }
                    is StateView.Error -> {

                    }
                }
            }
        }


    }

    private fun initRecycler() {
        genreMovieAdapter = GenreMovieAdapter(
            { genreId, name ->
                val action = HomeFragmentDirections.actionMenuHomeToMovieGenreFragment(genreId, name)
                findNavController().navigate(action)

            },
            movieClickListener =  { movieId ->
                movieId?.let {
                    val action = MainGraphDirections.actionGlobalMovieDetailsFragment(movieId)
                    findNavController().navigate(action)
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