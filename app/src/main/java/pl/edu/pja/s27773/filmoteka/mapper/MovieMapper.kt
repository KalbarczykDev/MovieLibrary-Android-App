package pl.edu.pja.s27773.filmoteka.mapper

import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository

/**
 * Maps a [MovieDto] to a domain-level [Movie] object.
 *
 * Applies validation to fields such as title, release date, and rating.
 * If the DTO ID is null, generates a new unique ID using the repository.
 *
 * @return A validated [Movie] instance.
 */
fun MovieDto.toDomain(): Movie = Movie(
    id = MovieId.of(id ?: MovieRepository.nextId().toString().toInt()),
    title = MovieTitle.of(title),
    releaseDate = MovieReleaseDate.of(releaseDate),
    category = category,
    status = status,
    rating = rating?.let { MovieRating.of(it) },
    posterUri = posterUri
)

/**
 * Maps a domain-level [Movie] to a [MovieDto].
 *
 * Extracts primitive values from value classes to simplify use in UI or storage.
 *
 * @return A [MovieDto] representation of the movie.
 */
fun Movie.toDto(): MovieDto = MovieDto(
    id = id.toString().toInt(),
    title = title.toString(),
    releaseDate = releaseDate.value,
    category = category,
    status = status,
    rating = rating?.value,
    posterUri = posterUri
)
