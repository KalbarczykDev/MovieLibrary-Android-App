package pl.edu.pja.s27773.filmoteka.error

import pl.edu.pja.s27773.filmoteka.R

sealed class MovieCrudError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    object IdTaken : MovieCrudError(R.string.error_movie_id_taken, "Movie ID is already taken")
    object NotFound : MovieCrudError(R.string.error_movie_not_found, "Movie not found")
    object CategoryNotSelected : MovieCrudError(R.string.error_category_not_selected, "Category not selected")
    object StatusNotSelected : MovieCrudError(R.string.error_status_not_selected, "Status not selected")
    object RatingNotSetForWatchedMovie : MovieCrudError(
        R.string.error_rating_not_set_for_watched_movie,
        "Rating not set for watched movie"
    )
}
