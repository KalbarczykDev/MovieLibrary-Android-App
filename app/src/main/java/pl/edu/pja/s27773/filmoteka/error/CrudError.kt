package pl.edu.pja.s27773.filmoteka.error

enum class MovieCrudError(override val stringResKey: String) : AppError {
    ID_TAKEN("error_movie_id_taken"),
    NOT_FOUND("error_movie_not_found"),
}