package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.TitleError

@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        private fun validate(value: String) {
            if (value.isBlank()) throw throw AppErrorException(TitleError.Blank)
            if(value.length > 255) throw AppErrorException(TitleError.TooLong)

        }

        fun of(value: String): Title {
            validate(value)
            return Title(value.trim())
        }
    }

    override fun toString(): String = value
}
