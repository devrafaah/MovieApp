package com.example.movieapp.presenter.main.bottomBar.download

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDownloadBinding
import com.example.movieapp.presenter.main.bottomBar.download.adapter.DownloadMovieAdapter
import com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.MovieGenreFragmentDirections
import com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.adapter.MovieLargeAdapter
import com.example.movieapp.util.hideKeyboard
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DownloadFragment : Fragment() {

    private var _binding : FragmentDownloadBinding? = null
    private val binding get() = _binding!!

    private lateinit var downloadMovieAdapter: DownloadMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadBinding.inflate(
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
        downloadMovieAdapter = DownloadMovieAdapter(
            context = requireContext(),
            detailsClickListener = { movieId ->
                movieId?.let {
                    val action = MovieGenreFragmentDirections.actionGlobalMovieDetailsFragment(it)
                    findNavController().navigate(action)
                }
            },
            deleteClickListener = { movieId ->
            }
        )
        with(binding.rvMovie) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = downloadMovieAdapter
        }
    }



    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_view, menu);
        val item = menu.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun initSearchView() {
        binding.searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //getMoviesBySearch(query)
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("SimpleSearchView", "Text changed:$newText")
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                Log.d("SimpleSearchView", "Text cleared")
                return false
            }
        })
        binding.searchView.setOnSearchViewListener(object : SimpleSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                Log.d("SimpleSearchView", "onSearchViewShown")
            }

            override fun onSearchViewClosed() {
                //getMoviesByGenres()
            }

            override fun onSearchViewShownAnimation() {
                Log.d("SimpleSearchView", "onSearchViewShownAnimation")
            }

            override fun onSearchViewClosedAnimation() {
                Log.d("SimpleSearchView", "onSearchViewClosedAnimation")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}