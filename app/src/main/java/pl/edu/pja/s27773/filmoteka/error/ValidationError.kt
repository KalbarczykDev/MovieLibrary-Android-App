package pl.edu.pja.s27773.filmoteka.error


sealed class TitleError(
    override val stringResKey: String,
    val debugMessage: String
) : AppError {

    data object Blank : TitleError("error_title_blank", "Title is blank")
    data object TooLong : TitleError("error_title_too_long", "Title exceeds max length")
}


sealed class RatingError(
    override val stringResKey: String,
    val debugMessage: String
) : AppError {

    data object Invalid : RatingError("error_rating_invalid", "Rating value is invalid")
    data object MissingForWatched : RatingError("error_rating_required", "Rating is required for watched movies")
}


sealed class CommentError(
    override val stringResKey: String,
    val debugMessage: String
) : AppError {

    data object TooLong : CommentError("error_comment_too_long", "Comment exceeds character limit")
    data object Blank : CommentError("error_comment_blank", "Comment is blank")
}

sealed class IdError(
    override val stringResKey: String,
    val debugMessage: String
) : AppError {

    data object Invalid : IdError("error_id_invalid", "ID is not valid")
}


sealed class ReleaseDateError(
    override val stringResKey: String,
    val debugMessage: String
) : AppError {

    data object FutureReleaseDate : ReleaseDateError("error_release_date_future", "Release date is in the future")
}
