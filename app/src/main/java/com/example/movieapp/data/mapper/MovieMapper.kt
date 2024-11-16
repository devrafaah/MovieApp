package com.example.movieapp.data.mapper

import com.example.movieapp.data.local.entity.MovieEntity
import com.example.movieapp.data.model.countries.CountryResponse
import com.example.movieapp.data.model.genre.GetGenre
import com.example.movieapp.data.model.movie.GetMovie
import com.example.movieapp.data.model.movie_credits.GetCredit
import com.example.movieapp.data.model.movie_credits.GetMovieCast
import com.example.movieapp.data.model.reviewMovie.GetAuthorDetails
import com.example.movieapp.data.model.reviewMovie.GetReviewMovie
import com.example.movieapp.domain.model.AuthorDetails
import com.example.movieapp.domain.model.Country
import com.example.movieapp.domain.model.Credit
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieCast
import com.example.movieapp.domain.model.ReviewMovie
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
        genres = genres?.map { it.toDomain() },
        runtime = runtime
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

fun GetAuthorDetails.toDomain() : AuthorDetails {
    return AuthorDetails(
        avatarPath = "https://image.tmdb.org/t/p/original$avatarPath",
        name = name,
        rating = rating,
        username = username
    )
}

fun GetReviewMovie.toDomain() = ReviewMovie(
    author = author,
    authorDetails = authorDetails?.toDomain(),
    content = content,
    createdAt = createdAt,
    id = id,
    updatedAt = updatedAt,
    url = url
)


fun Movie.toEntity() = MovieEntity(
    id = id,
    title = title,
    poster = posterPath,
    runtime = runtime,
    insertion = System.currentTimeMillis()
)

fun MovieEntity.toDomain() = Movie(
    id = id,
    title = title,
    posterPath = poster,
    runtime = runtime,
)