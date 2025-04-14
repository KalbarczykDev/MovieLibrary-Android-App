package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.TitleError

/**
 * Value class representing the title of a movie.
 *
 * Ensures that the title is non-blank and does not exceed 255 characters.
 */
@JvmInline
value class MovieTitle private constructor(val value: String) {
    companion object {
        /**
         * Validates the title string for required constraints.
         *
         * @param value The raw title string.
         * @throws AppErrorException If the title is blank or too long.
         */
        private fun validate(value: String) {
            if (value.isBlank()) throw throw AppErrorException(TitleError.Blank)
            if (value.length > 255) throw AppErrorException(TitleError.TooLong)

        }

        /**
         * Creates a validated [MovieTitle] instance.
         *
         * @param value The raw title string.
         * @return A [MovieTitle] object with trimmed and validated value.
         * @throws AppErrorException If validation fails.
         */
        fun of(value: String): MovieTitle {
            validate(value)
            return MovieTitle(value.trim())
        }
    }

    /**
     * Returns the string representation of the movie title.
     */
    override fun toString(): String = value
}
