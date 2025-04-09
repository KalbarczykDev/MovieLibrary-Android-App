package pl.edu.pja.s27773.filmoteka.error

enum class MovieCrudError(override val stringResKey: String) : AppError {
    ID_TAKEN("error_movie_id_taken"),
    CREATE("error_movie_create"),
    UPDATE("error_movie_update"),
    DELETE("error_movie_delete"),
    NOT_FOUND("error_movie_not_found"),
}