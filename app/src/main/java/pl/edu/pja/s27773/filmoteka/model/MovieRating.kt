package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.RatingError
import pl.edu.pja.s27773.filmoteka.error.AppErrorException

/**
 * Value class representing the rating of a movie.
 *
 * Ensures that the rating value is within the range of 0 to 5.
 */
@JvmInline
value class MovieRating private constructor(val value: Float) {
    companion object {
        /**
         * Validates the rating value.
         *
         * @param value The float value to validate.
         * @throws AppErrorException If the rating is less than 0 or greater than 5.
         */
        private fun validate(value: Float) {
            if (value < 0 || value > 5) throw AppErrorException(RatingError.Invalid)
        }

        /**
         * Creates a validated [MovieRating] instance.
         *
         * @param value The float value to wrap.
         * @return A [MovieRating] object.
         * @throws AppErrorException If validation fails.
         */
        fun of(value: Float): MovieRating {
            validate(value)
            return MovieRating(value)
        }
    }

    /**
     * Returns the string representation of the rating.
     */
    override fun toString(): String = "$value"
}