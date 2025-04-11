package pl.edu.pja.s27773.filmoteka.error

sealed class MovieCrudError(
    override val stringResKey: String,
    val debugMessage: String
) : AppError {

    object IdTaken : MovieCrudError("error_movie_id_taken", "Movie ID is already taken")
    object NotFound : MovieCrudError("error_movie_not_found", "Movie not found")
    object CategoryNotSelected : MovieCrudError("error_category_not_selected", "Category not selected")
    object StatusNotSelected : MovieCrudError("error_status_not_selected", "Status not selected")
}
