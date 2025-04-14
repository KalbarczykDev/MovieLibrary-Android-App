package pl.edu.pja.s27773.filmoteka.model.dto

import android.net.Uri
import pl.edu.pja.s27773.filmoteka.model.MovieCategory
import pl.edu.pja.s27773.filmoteka.model.MovieStatus
import java.time.LocalDate

/**
 * Data Transfer Object (DTO) representing a movie for UI or service layers.
 *
 * Used to transfer raw, editable movie data without domain-level validation.
 *
 * @property id Optional movie ID (null for new movies).
 * @property title Title of the movie as a plain string.
 * @property releaseDate Release date of the movie.
 * @property category Movie category (e.g., Movie, Series, Documentary).
 * @property status Movie watch status (e.g., Watched, Not Watched).
 * @property rating Optional user rating between 0 and 5 (only for watched movies).
 * @property posterUri Optional URI pointing to the movie's poster image.
 */
data class MovieDto(
    val id: Int?,
    val title: String,
    val releaseDate: LocalDate,
    val category: MovieCategory,
    val status: MovieStatus,
    val rating: Float?,
    val posterUri: Uri?
)