package pl.edu.pja.s27773.filmoteka.error

import pl.edu.pja.s27773.filmoteka.R

/**
 * Represents errors that can occur during movie CRUD operations.
 *
 * Each error provides a user-friendly string resource and a developer-friendly debug message.
 *
 * @property stringResKey Resource ID for the localized error message.
 * @property debugMessage Developer-facing error description for logging and debugging.
 */
sealed class MovieCrudError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    /** Error when a movie with the same ID already exists in the repository. */
    object IdTaken : MovieCrudError(R.string.error_movie_id_taken, "Movie ID is already taken")

    /** Error when a movie is not found in the repository. */
    object NotFound : MovieCrudError(R.string.error_movie_not_found, "Movie not found")

    /** Error when no category is selected for the movie. */
    object CategoryNotSelected : MovieCrudError(R.string.error_category_not_selected, "Category not selected")

    /** Error when no status is selected for the movie. */
    object StatusNotSelected : MovieCrudError(R.string.error_status_not_selected, "Status not selected")

    /** Error when a watched movie is missing a rating. */
    object RatingNotSetForWatchedMovie : MovieCrudError(
        R.string.error_rating_not_set_for_watched_movie,
        "Rating not set for watched movie"
    )
}
