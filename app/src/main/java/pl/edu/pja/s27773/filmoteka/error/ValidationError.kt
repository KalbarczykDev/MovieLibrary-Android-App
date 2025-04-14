package pl.edu.pja.s27773.filmoteka.error

import pl.edu.pja.s27773.filmoteka.R

/**
 * Represents validation errors related to movie titles.
 *
 * @property stringResKey Resource ID for the user-friendly error message.
 * @property debugMessage Developer-friendly error message for debugging.
 */
sealed class TitleError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    /** Error when the title is blank. */
    data object Blank : TitleError(R.string.error_title_blank, "Title is blank")

    /** Error when the title exceeds the allowed length. */
    data object TooLong : TitleError(R.string.error_title_to_long, "Title exceeds max length")
}

/**
 * Represents validation errors related to movie ratings.
 *
 * @property stringResKey Resource ID for the user-facing message.
 * @property debugMessage Developer-friendly error description.
 */
sealed class RatingError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    /** Error when the rating value is out of the valid range. */
    data object Invalid : RatingError(R.string.error_rating_invalid, "Rating value is invalid")
}

/**
 * Represents validation errors related to movie identifiers.
 *
 * @property stringResKey Resource ID for the user-facing message.
 * @property debugMessage Developer-friendly error description.
 */
sealed class IdError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    /** Error when the movie ID is less than or equal to zero. */
    data object Invalid : IdError(R.string.error_id_invalid, "ID is not valid")
}

/**
 * Represents validation errors related to movie release dates.
 *
 * @property stringResKey Resource ID for the user-facing message.
 * @property debugMessage Developer-friendly error description.
 */
sealed class ReleaseDateError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {

    /** Error when the release date is more than 2 years in the future. */
    data object FutureReleaseDate :
        ReleaseDateError(R.string.error_release_date_future, "Release date is in the future")
}
