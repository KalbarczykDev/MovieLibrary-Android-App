package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.RatingError
import pl.edu.pja.s27773.filmoteka.error.AppError
import pl.edu.pja.s27773.filmoteka.error.AppErrorException

@JvmInline
value class Rating private constructor(val value: Float) {
    companion object {
        private fun validate(value: Float) {
            if (value < 0 || value > 10) throw AppErrorException(RatingError.Invalid)
        }

        fun of(value: Float): Rating {
            validate(value)
            return Rating(value)
        }
    }

    override fun toString(): String = "$value"
}