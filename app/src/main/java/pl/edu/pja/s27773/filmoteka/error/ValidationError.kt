package pl.edu.pja.s27773.filmoteka.error

import pl.edu.pja.s27773.filmoteka.R


sealed class TitleError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    data object Blank : TitleError(R.string.error_title_blank, "Title is blank")
    data object TooLong : TitleError(R.string.error_title_to_long, "Title exceeds max length")
}


sealed class RatingError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    data object Invalid : RatingError(R.string.error_rating_invalid, "Rating value is invalid")
}

sealed class IdError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    data object Invalid : IdError(R.string.error_id_invalid, "ID is not valid")
}


sealed class ReleaseDateError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    data object FutureReleaseDate : ReleaseDateError(R.string.error_release_date_future, "Release date is in the future")
}
