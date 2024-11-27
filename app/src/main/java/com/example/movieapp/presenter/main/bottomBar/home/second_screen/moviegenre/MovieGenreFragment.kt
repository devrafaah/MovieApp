package com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import br.com.hellodev.movieapp.presenter.main.moviegenre.adapter.LoadStatePagingAdapter
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieGenreBinding
import com.example.movieapp.presenter.main.bottomBar.home.second_screen.moviegenre.adapter.PagingMovieDataAdapter
import com.example.movieapp.util.hideKeyboard
import com.example.movieapp.util.initToolbar
import com.example.movieapp.util.navigateWithAnimations
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieGenreFragment : Fragment() {

    private var _binding: FragmentMovieGenreBinding? = null

    private val binding get() = _binding!!

    private val viewmodel: MovieGenreViewModel by viewModels()
    private val args: MovieGenreFragmentArgs by navArgs()
    private lateinit var pagingMovieAdapter: PagingMovieDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(toolbar = binding.toolbar)
        initRecycler()
        binding.toolbar.title = args.genreName
        initSearchView()
        getMoviesByGenrePagination()
    }

    private fun initRecycler() {
        pagingMovieAdapter = PagingMovieDataAdapter( movieClickListener = { movieId ->
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
        binding.searchView.setOnQueryTextListener(object :  SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getMoviesBySearch(query)
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
        binding.searchView.setOnSearchViewListener(object :  SimpleSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                Log.d("SimpleSearchView", "onSearchViewShown")
            }

            override fun onSearchViewClosed() {
                getMoviesByGenrePagination()
            }

            override fun onSearchViewShownAnimation() {
                Log.d("SimpleSearchView", "onSearchViewShownAnimation")
            }

            override fun onSearchViewClosedAnimation() {
                Log.d("SimpleSearchView", "onSearchViewClosedAnimation")
            }
        })
    }


    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_view, menu);
        val item = menu.findItem(R.id.action_search)
        binding.searchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getMoviesByGenrePagination(forceRequest: Boolean = false) {
        lifecycleScope.launch {
            viewmodel.getMoviesByGenrePaginationUseCase(
                genreId = args.genreId,
                forceRequest = forceRequest
            )
            viewmodel.movieList.collectLatest { pagingData ->
                pagingMovieAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
//        viewmodel.getMoviesGenres(args.genreId).observe(viewLifecycleOwner) { stateView ->
//            when (stateView) {
//                is StateView.Loading -> {
//                    binding.rvMovie.isVisible = false
//                    binding.progressBar.isVisible = true
//                }
//                is StateView.Success -> {
//                    binding.progressBar.isVisible = false
//                    movieAdapter.submitList(stateView.data)
//                    binding.rvMovie.isVisible = true
//                }
//                is StateView.Error -> {
//                    binding.progressBar.isVisible = false
//                }
//            }
//        }
    }

    private fun getMoviesBySearch(query: String?) {
        lifecycleScope.launch {
            viewmodel.getMoviesGenresBySearch(query).collectLatest { pagingData ->
                pagingMovieAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
