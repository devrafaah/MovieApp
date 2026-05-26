package com.example.movieapp.presenter.main.bottomBar.home.first_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.mapper.toDomain
import com.example.movieapp.domain.model.movie.Genre
import com.example.movieapp.domain.usecase.movie.GetGenresUseCase
import com.example.movieapp.domain.usecase.movie.GetMoviesByGenreUseCase
import com.example.movieapp.presenter.model.MoviesByGenre
import com.example.movieapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase
) : ViewModel() {

    private val _movieList = MutableLiveData<List<MoviesByGenre>>()
    val movieList: LiveData<List<MoviesByGenre>>
        get() = _movieList

    private val _homeState = MutableLiveData<StateView<Unit>>()
    val homeState: LiveData<StateView<Unit>>
        get() = _homeState


    init {
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch {
            try {
                _homeState.postValue(StateView.Loading())

                Log.d("INFOTESTE", "getGenres: buscando gêneros...")
                val genres = getGenresUseCase.invoke()
                Log.d("INFOTESTE", "getGenres: total recebido = ${genres.size}")
                Log.d("INFOTESTE", "getGenres: lista = $genres")

                if (genres.isEmpty()) {
                    Log.w("INFOTESTE", "getGenres: lista de gêneros vazia!")
                }

                getMoviesByGenre(genres)
            } catch (e: Exception) {
                Log.e("INFOTESTE", "getGenres: ERRO = ${e.message}", e)
                _homeState.postValue(StateView.Error(e.message))
            }
        }
    }

    private fun getMoviesByGenre(genres: List<Genre>) {
        val moviesByGenre: MutableList<MoviesByGenre> = mutableListOf()
        viewModelScope.launch {
            genres.forEach { genre ->
                try {
                    Log.d("INFOTESTE", "getMoviesByGenre: buscando filmes do gênero '${genre.name}' (id=${genre.id})")
                    val movies = getMoviesByGenreUseCase(genreId = genre.id)
                    Log.d("INFOTESTE", "getMoviesByGenre: '${genre.name}' -> ${movies.size} filmes recebidos")

                    val movieByGenre = MoviesByGenre(
                        id = genre.id,
                        name = genre.name,
                        movies = movies.map { it.toDomain() }.take(5)
                    )
                    moviesByGenre.add(movieByGenre)

                    if (moviesByGenre.size == genres.size) {
                        Log.d("INFOTESTE", "getMoviesByGenre: todos os gêneros carregados (${moviesByGenre.size})")
                        _movieList.postValue(moviesByGenre)
                        _homeState.postValue(StateView.Success(Unit))
                    }
                } catch (e: Exception) {
                    Log.e("INFOTESTE", "getMoviesByGenre: ERRO no gênero '${genre.name}' = ${e.message}", e)
                    _homeState.postValue(StateView.Error(e.message))
                }
            }
        }
    }

}