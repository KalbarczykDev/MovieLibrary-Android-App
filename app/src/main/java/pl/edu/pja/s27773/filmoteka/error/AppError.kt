package pl.edu.pja.s27773.filmoteka.error

import pl.edu.pja.s27773.filmoteka.R

interface AppError {
    val stringResKey: Int
    val debugMessage: String
}

sealed class DefaultAppError(
    override val stringResKey: Int,
    override val debugMessage: String
) : AppError {
    object Default : DefaultAppError(R.string.unknown_error, "Something Went Wrong")
}

class AppErrorException(
    override val message: String, //debug message
    val stringResKey: Int
) : Exception(message) {
    constructor(error: AppError) : this(error.debugMessage, error.stringResKey)
}
