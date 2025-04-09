package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.RatingError
import pl.edu.pja.s27773.filmoteka.error.AppError

@JvmInline
value class Rating private constructor(val value: Int) {
    companion object {
        private fun validate(value: Int): RatingError? {
            return if (value !in 1..10) RatingError.INVALID else null
        }

        fun of(value: Int): Rating {
            val error = validate(value)
            require(error == null) { error?.stringResKey ?: AppError.DEFAULT }
            return Rating(value)
        }
    }

    override fun toString(): String = "$value/10"
}