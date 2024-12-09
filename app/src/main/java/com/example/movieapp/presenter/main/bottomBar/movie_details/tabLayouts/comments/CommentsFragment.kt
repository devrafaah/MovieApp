package com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.comments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentCommentsBinding
import com.example.movieapp.presenter.main.bottomBar.movie_details.MovieDetailsViewModel
import com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.adapter.CommentReviewAdapter
import com.example.movieapp.util.StateView
import com.example.movieapp.util.applyScreenWindowInsets
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CommentsFragment : Fragment() {


    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var commentAdapter: CommentReviewAdapter
    private val movieDetailsViewModel: MovieDetailsViewModel by activityViewModels()
    private val viewModel : CommentsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyScreenWindowInsets(view = view, applyTop = false)
        initListeners()
    }

    private fun initListeners() {
        initRecyclerView()
        initObserver()

    }
    private fun initObserver() {
        movieDetailsViewModel.movieId.observe(viewLifecycleOwner) { movieId ->
            getReviewsComments(movieId)
        }
    }
    private fun initRecyclerView() {
        commentAdapter = CommentReviewAdapter()

        with(binding.rvMovieComments) {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
    private fun getReviewsComments(movieId: Int) {
        viewModel.getCommentsReviews(movieId).observe(viewLifecycleOwner) { stateView ->
            when(stateView) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    commentAdapter.submitList(stateView.data)
                    Log.i("comentarios", "getReviewsComments: ${stateView.data}")
                }
                is StateView.Error -> {

                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}