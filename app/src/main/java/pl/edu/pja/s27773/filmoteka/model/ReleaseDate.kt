package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.ReleaseDateError
import java.time.LocalDate

@JvmInline
value class ReleaseDate private constructor(val value: LocalDate) {
    companion object {
        fun of(value: LocalDate): ReleaseDate {
            require(!value.isAfter(LocalDate.now().plusYears(2))) {
                 ReleaseDateError.RELEASE_DATE_FUTURE.stringResKey
            }
            return ReleaseDate(value)
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}