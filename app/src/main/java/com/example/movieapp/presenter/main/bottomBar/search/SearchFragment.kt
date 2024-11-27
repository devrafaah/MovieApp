package com.example.movieapp.presenter.main.bottomBar.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import br.com.hellodev.movieapp.presenter.main.moviegenre.adapter.LoadStatePagingAdapter
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.MovieGenreFragmentDirections
import com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.adapter.PagingMovieDataAdapter
import com.example.movieapp.util.hideKeyboard
import com.example.movieapp.util.navigateWithAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {


    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: SearhViewModel by viewModels()
    private lateinit var pagingMovieAdapter: PagingMovieDataAdapter

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
        initRecycler()
        initSearchView()
    }


    private fun initRecycler() {
        pagingMovieAdapter = PagingMovieDataAdapter(movieClickListener = { movieId ->
            movieId?.let {
                val action =
                    MovieGenreFragmentDirections.actionGlobalMovieDetailsFragment(movieId)
                findNavController().navigateWithAnimations(action)
            }
        })
        lifecycleScope.launch {
            pagingMovieAdapter.loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {

                        binding.shimmer.startShimmer()
                        binding.shimmer.isVisible = true
                        binding.rvMovie.isVisible = false
                    }

                    is LoadState.NotLoading -> {

                        binding.shimmer.stopShimmer()
                        binding.shimmer.isVisible = false
                        binding.rvMovie.isVisible = true


                        emptyState(pagingMovieAdapter.itemCount == 0)

                    }

                    is LoadState.Error -> {
                        binding.shimmer.stopShimmer()
                        binding.shimmer.isVisible = false
                        binding.rvMovie.isVisible = false
                        val error = (loadState.refresh as LoadState.Error).error.message
                            ?: "Ocorreu um erro tente novamente mais tarde"
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        with(binding.rvMovie) {
            setHasFixedSize(true)

            val mGridLayout = GridLayoutManager(requireContext(), 2)
            layoutManager = mGridLayout

            val footerAdapter = pagingMovieAdapter.withLoadStateFooter(
                footer = LoadStatePagingAdapter()
            )
            adapter = footerAdapter

            mGridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == pagingMovieAdapter.itemCount && footerAdapter.itemCount > 0) {
                        2
                    } else {
                        1
                    }
                }
            }
        }
    }

    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                hideKeyboard()
                if (query.isNotEmpty()) {
                    getMoviesBySearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun getMoviesBySearch(query: String?) {
        lifecycleScope.launch {
            viewmodel.getMoviesGenresBySearch(query).collectLatest { pagingData ->
                pagingMovieAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    private fun emptyState(empty: Boolean) {
        binding.rvMovie.isVisible = !empty
        binding.layoutEmpty.isVisible = empty
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}