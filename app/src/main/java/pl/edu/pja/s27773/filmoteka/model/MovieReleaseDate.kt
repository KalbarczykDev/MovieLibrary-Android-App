package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.ReleaseDateError
import java.time.LocalDate

@JvmInline
value class MovieReleaseDate private constructor(val value: LocalDate) {
    companion object {
        private fun validate(value: LocalDate) {
            if (value.isAfter(LocalDate.now().plusYears(2))) {
                throw AppErrorException(ReleaseDateError.FutureReleaseDate)
            }
        }

        fun of(value: LocalDate): MovieReleaseDate {
            validate(value)
            return MovieReleaseDate(value)
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}