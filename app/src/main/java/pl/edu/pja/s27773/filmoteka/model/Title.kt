package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.TitleError
import pl.edu.pja.s27773.filmoteka.error.ValidationError

@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        private fun validate(value: String): TitleError? {
            return when {
                value.isBlank() -> TitleError.BLANK
                value.length > 255 -> TitleError.TOO_LONG
                else -> null
            }
        }

        fun of(value: String): Title {
            val error = validate(value)
            require(error == null) { error?.stringResKey ?: ValidationError.DEFAULT }
            return Title(value.trim())
        }
    }

    override fun toString(): String = value
}
