package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.ReleaseDateError
import java.time.LocalDate

/**
 * Value class representing the release date of a movie.
 *
 * Validates that the date is not more than two years in the future.
 */
@JvmInline
value class MovieReleaseDate private constructor(val value: LocalDate) {
    companion object {
        /**
         * Validates the release date.
         *
         * @param value The [LocalDate] to validate.
         * @throws AppErrorException If the date is more than 2 years in the future.
         */
        private fun validate(value: LocalDate) {
            if (value.isAfter(LocalDate.now().plusYears(2))) {
                throw AppErrorException(ReleaseDateError.FutureReleaseDate)
            }
        }

        /**
         * Creates a validated [MovieReleaseDate] instance.
         *
         * @param value The [LocalDate] to wrap.
         * @return A validated [MovieReleaseDate] object.
         * @throws AppErrorException If validation fails.
         */
        fun of(value: LocalDate): MovieReleaseDate {
            validate(value)
            return MovieReleaseDate(value)
        }
    }

    /**
     * Returns the string representation of the release date.
     */
    override fun toString(): String {
        return value.toString()
    }
}