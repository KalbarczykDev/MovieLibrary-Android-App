package pl.edu.pja.s27773.filmoteka.model

import pl.edu.pja.s27773.filmoteka.error.CommentError
import pl.edu.pja.s27773.filmoteka.error.AppError

@JvmInline
value class Comment private constructor(val value: String) {
    companion object {
        private const val MAX_LENGTH = 250

        private fun validate(value: String): CommentError? = when {
            value.isBlank() -> CommentError.BLANK
            value.length > MAX_LENGTH -> CommentError.TOO_LONG
            else -> null
        }

        fun of(value: String): Comment {
            val error = validate(value)
            require(error == null) { error?.stringResKey ?: AppError.DEFAULT }
            return Comment(value.trim())
        }
    }

    override fun toString(): String = value
}

