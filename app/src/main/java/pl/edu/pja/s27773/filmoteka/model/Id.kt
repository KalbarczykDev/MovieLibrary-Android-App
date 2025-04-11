package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.IdError
import pl.edu.pja.s27773.filmoteka.error.AppError
import pl.edu.pja.s27773.filmoteka.error.AppErrorException

//optymalizacja dla wrapperów
@JvmInline
value class Id private constructor(val value: Int) {
    companion object {
        private fun validate(value: Int) {
            if (value <= 0) throw AppErrorException(IdError.Invalid)
        }


        fun of(value: Int): Id {
            validate(value)
            return Id(value)
        }
    }

    override fun toString(): String = value.toString()
}