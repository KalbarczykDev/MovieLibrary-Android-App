package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.IdError
import pl.edu.pja.s27773.filmoteka.error.AppErrorException

/**
 * Value class representing the unique identifier of a movie.
 *
 * Validates that the ID is a positive integer greater than 0.
 */
@JvmInline
value class MovieId private constructor(val value: Int) {
    companion object {
        /**
         * Validates the movie ID.
         *
         * @param value The integer ID to validate.
         * @throws AppErrorException If the ID is less than or equal to 0.
         */
        private fun validate(value: Int) {
            if (value <= 0) throw AppErrorException(IdError.Invalid)
        }

        /**
         * Creates a validated [MovieId] instance.
         *
         * @param value The integer ID to wrap.
         * @return A [MovieId] object.
         * @throws AppErrorException If validation fails.
         */
        fun of(value: Int): MovieId {
            validate(value)
            return MovieId(value)
        }
    }

    /**
     * Returns the string representation of the movie ID.
     */
    override fun toString(): String = value.toString()
}