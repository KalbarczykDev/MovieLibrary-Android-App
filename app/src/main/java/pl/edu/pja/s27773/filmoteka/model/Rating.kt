package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.RatingError
import pl.edu.pja.s27773.filmoteka.error.AppError
import pl.edu.pja.s27773.filmoteka.error.AppErrorException

@JvmInline
value class Rating private constructor(val value: Int) {
    companion object {
        private fun validate(value: Int) {
            if (value !in 1..10) throw AppErrorException(RatingError.Invalid)
        }

        fun of(value: Int): Rating {
            validate(value)
            return Rating(value)
        }
    }

    override fun toString(): String = "$value"
}