package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.IdError
import pl.edu.pja.s27773.filmoteka.error.AppErrorException

//optymalizacja dla wrapperów
@JvmInline
value class MovieId private constructor(val value: Int) {
    companion object {
        private fun validate(value: Int) {
            if (value <= 0) throw AppErrorException(IdError.Invalid)
        }


        fun of(value: Int): MovieId {
            validate(value)
            return MovieId(value)
        }
    }

    override fun toString(): String = value.toString()
}