package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.IdError
import pl.edu.pja.s27773.filmoteka.error.AppError

//optymalizacja dla wrapperów
@JvmInline
value class Id private constructor(val value: Int) {
    companion object {
        private fun validate(value: Int): IdError? =
            if (value <= 0) IdError.INVALID else null

        fun of(value: Int): Id {
            val error = validate(value)
            require(error == null) { error?.stringResKey ?: AppError.DEFAULT }
            return Id(value)
        }
    }

    override fun toString(): String = value.toString()
}