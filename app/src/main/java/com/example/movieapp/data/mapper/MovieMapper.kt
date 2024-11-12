package com.example.movieapp.data.mapper

import com.example.movieapp.data.model.countries.CountryResponse
import com.example.movieapp.data.model.genre.GetGenre
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.model.movie_credits.GetCredit
import com.example.movieapp.data.model.movie_credits.GetMovieCast
import com.example.movieapp.domain.model.Country
import com.example.movieapp.domain.model.Credit
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieCast
import com.example.movieapp.presenter.model.GenrePresentation


fun GetGenre.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun GetMovie.toDomain(): Movie {
    return Movie(
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        productionCountries = productionCountries?.map { it.toDomain() },
        genres = genres?.map { it.toDomain() }
    )
}

fun Genre.toPresentation(): GenrePresentation {
    return GenrePresentation(
        id = id,
        name = name,
        movies = emptyList()
    )
}

fun CountryResponse.toDomain() = Country(
    name = name,
    iso31661 = iso31661
)

fun GetMovieCast.toDomain() = MovieCast(
    adult = adult,
    castId = castId,
    character = character,
    creditId = creditId,
    gender = gender,
    id = id,
    knownForDepartment = knownForDepartment,
    name = name,
    order = order,
    originalName = originalName,
    popularity = popularity,
    profilePath = profilePath
)

fun GetCredit.toDomain() = Credit(
    id = id,
    cast = cast?.map { it.toDomain() }
)

