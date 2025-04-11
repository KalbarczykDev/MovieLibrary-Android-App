package pl.edu.pja.s27773.filmoteka.error

interface AppError {
    val stringResKey: String

    companion object {
        const val DEFAULT = "error_default"
    }
}


class AppErrorException(
    override val message: String,
    val error: AppError
) : Exception(message) {
    constructor(error: AppError) : this(error.stringResKey, error)
}
