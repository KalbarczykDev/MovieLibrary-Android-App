package pl.edu.pja.s27773.filmoteka.model

import java.time.LocalDate

@JvmInline
value class ReleaseDate private constructor(val value: LocalDate) {
    companion object {
        fun of(value: LocalDate): ReleaseDate {
            require(!value.isAfter(LocalDate.now().plusYears(2))) {
                "Release date cannot be more than 2 years in the future"
            }
            return ReleaseDate(value)
        }
    }

    override fun toString(): String {
        return value.toString()
    }
}