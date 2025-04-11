package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.ReleaseDateError
import java.time.LocalDate

@JvmInline
value class ReleaseDate private constructor(val value: LocalDate) {
    companion object {
        private fun validate(value: LocalDate) {
            if (value.isAfter(LocalDate.now().plusYears(2))) {
                throw AppErrorException(ReleaseDateError.FutureReleaseDate)
            }
        }

        fun of(value: LocalDate): ReleaseDate {
            validate(value)
            return ReleaseDate(value)
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}