package pl.edu.pja.s27773.filmoteka.model

import android.net.Uri
import androidx.annotation.StringRes
import pl.edu.pja.s27773.filmoteka.R

/**
 * Represents a movie entity in the application.
 *
 * @property id Unique identifier for the movie.
 * @property title Title of the movie.
 * @property releaseDate Release date of the movie.
 * @property category Category of the movie (e.g., Movie, Series, Documentary).
 * @property status Watch status of the movie (e.g., Watched, Not Watched).
 * @property rating Optional user rating (0–5) for watched movies.
 * @property posterUri Optional URI to the movie poster image.
 */
data class Movie(
    val id: MovieId,
    var title: MovieTitle,
    var releaseDate: MovieReleaseDate,
    var category: MovieCategory,
    var status: MovieStatus,
    var rating: MovieRating? = null,
    var posterUri: Uri? = null
)

/**
 * Enum representing the category of a movie.
 *
 * @property stringResId Resource ID for the localized display string.
 */
enum class MovieCategory(@StringRes val stringResId: Int) {
    MOVIE(R.string.category_movie),
    SERIES(R.string.category_series),
    DOCUMENTARY(R.string.category_documentary),
    NONE(R.string.category_none)
}

/**
 * Enum representing the watch status of a movie.
 *
 * @property stringResId Resource ID for the localized display string.
 */
enum class MovieStatus(@StringRes val stringResId: Int) {
    WATCHED(R.string.status_watched),
    NOT_WATCHED(R.string.status_not_watched),
    NONE(R.string.status_none)
}










