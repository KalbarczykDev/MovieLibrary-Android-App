package pl.edu.pja.s27773.filmoteka.mapper

import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository

fun MovieDto.toDomain(): Movie = Movie(
    id = MovieId.of(id ?: MovieRepository.nextId().toString().toInt()),
    title = MovieTitle.of(title),
    releaseDate = MovieReleaseDate.of(releaseDate),
    category = category,
    status = status,
    rating = rating?.let { MovieRating.of(it) },
    posterUri = posterUri
)

fun Movie.toDto(): MovieDto = MovieDto(
    id = id.toString().toInt(),
    title = title.toString(),
    releaseDate = releaseDate.value,
    category = category,
    status = status,
    rating = rating?.value,
    posterUri = posterUri
)
