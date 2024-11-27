package com.example.movieapp.presenter.main.bottomBar.movie_details

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.DialogDownloadingBinding
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.presenter.main.bottomBar.movie_details.adapter.CastAdapter
import com.example.movieapp.presenter.main.bottomBar.movie_details.adapter.ViewPagerAdapter
import com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.comments.CommentsFragment
import com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.similar.SimilarFragment
import com.example.movieapp.presenter.main.bottomBar.movie_details.tabLayouts.trailers.TrailersFragment
import com.example.movieapp.util.StateView
import com.example.movieapp.util.ViewPager2ViewHeightAnimator
import com.example.movieapp.util.calculateFileSize
import com.example.movieapp.util.initToolbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val viewModel: MovieDetailsViewModel by activityViewModels()
    private lateinit var castAdapter: CastAdapter
    private lateinit var dialogDownloading: AlertDialog


    private lateinit var movie: Movie


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

    }

    private fun initListeners() {
        initToolbar(toolbar = binding.toolbar, lightIcon = true)
        getMovieDetails()
        getCredits()
        initRecyclerView()
        configTabLayout()
        binding.btnDownloadmovie.setOnClickListener {
            showDialogDownloading()
        }
    }

    private fun showDialogDownloading() {
        val dialogBinding = DialogDownloadingBinding.inflate(LayoutInflater.from(requireContext()))
        var progress = 0
        var downloaded = 0.0
        val movieDuration = movie.runtime?.toDouble() ?: 0.0

        lifecycleScope.launch {
            while (progress < 100 && isActive) {
                downloaded += (movieDuration / 100.0)
                dialogBinding.textDownloading.text = getString(
                    R.string.text_downloaded_size_dialog_downloading,
                    downloaded.calculateFileSize(),
                    movieDuration.calculateFileSize()
                )

                progress += 1
                dialogBinding.progressIndicator.progress = progress
                dialogBinding.textProgress.text = getString(
                    R.string.text_download_progress_dialog_downloading,
                    progress
                )

                kotlinx.coroutines.delay(30)
            }

            if (progress >= 100) {
                insertMovieLocal()
                dialogDownloading.dismiss()
            }
        }

        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
        dialogBinding.btnHide.setOnClickListener { dialogDownloading.dismiss() }
        dialogBinding.ibCancel.setOnClickListener { dialogDownloading.dismiss() }

        builder.setView(dialogBinding.root)
        dialogDownloading = builder.create()
        dialogDownloading.show()
    }


    private fun insertMovieLocal() {
        viewModel.insertMovieLocal(movie).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }

                is StateView.Success -> {
                    configData()
                }

                is StateView.Error -> {

                }

            }
        }
    }

    private fun getMovieDetails() {
        viewModel.getMovieDetails(movieId = args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }

                is StateView.Success -> {
                    stateView.data?.let {
                        this.movie = it
                        configData()
                    }
                }

                is StateView.Error -> {

                }

            }
        }
    }

    private fun getCredits() {
        viewModel.getMovieCreditsDetails(args.movieId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {

                }

                is StateView.Success -> {
                    castAdapter.submitList(stateView.data?.cast)
                }

                is StateView.Error -> {

                }

            }
        }
    }

    private fun initRecyclerView() {
        castAdapter = CastAdapter()

        with(binding.rvCrew) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    private fun configTabLayout() {
        viewModel.setMovieId(movieId = args.movieId)

        val adapter = ViewPagerAdapter(requireActivity())
        val mViewPager = ViewPager2ViewHeightAnimator()

        mViewPager.viewPager2 = binding.viewPager
        mViewPager.viewPager2?.adapter = adapter

        binding.viewPager.adapter = adapter

        adapter.addFragment(
            fragment = TrailersFragment(),
            title = R.string.title_trailerFragment
        )
        adapter.addFragment(
            fragment = SimilarFragment(),
            title = R.string.title_SimilarFragment
        )
        adapter.addFragment(
            fragment = CommentsFragment(),
            title = R.string.title_CommentsFragment
        )

        binding.viewPager.offscreenPageLimit = adapter.itemCount

        mViewPager.viewPager2?.let { viewPager ->
            TabLayoutMediator(
                binding.tabLayout, viewPager
            ) { tab, position ->
                tab.text = getString(adapter.getTitle(position))
            }.attach()
        }

    }

    private fun configData() {
        Glide
            .with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${movie.backdropPath}")
            .into(binding.moviePoster)

        binding.movieTitle.text = movie.title
        binding.movieVoteAverage.text = String.format(Locale.US, "%.1f", movie.voteAverage ?: 0.0)
        binding.movieReleaseDate.text = movie.releaseDate?.split("-")?.get(0) ?: "não informado"

        binding.movieProductionCountry.text =
            if (movie.productionCountries?.isNotEmpty() == true) {
                movie.productionCountries?.get(0)?.name.toString()
            } else {
                "País não informado"
            }

        binding.movieLang.text = movie.originalLanguage?.uppercase() ?: "Não informado"

        val genres = movie.genres?.joinToString(", ") { it.name } ?: "Não informado"
        binding.movieGenreList.text = String.format(Locale.US, "Gêneros: $genres")
        binding.movieSinopse.text = movie.overview ?: "Sinopse não disponível"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}