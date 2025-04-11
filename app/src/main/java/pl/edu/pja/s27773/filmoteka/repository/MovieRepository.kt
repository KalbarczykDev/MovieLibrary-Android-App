package pl.edu.pja.s27773.filmoteka.repository

import pl.edu.pja.s27773.filmoteka.error.AppErrorException
import pl.edu.pja.s27773.filmoteka.error.MovieCrudError
import pl.edu.pja.s27773.filmoteka.model.*

//sugar code dla  singletona
object MovieRepository {
    private val movies = mutableListOf<Movie>()

    fun getAll(): List<Movie> {
        return movies.toList()
    } //immutable list

    fun getById(id: MovieId): Movie? {
        return movies.find { it.id == id }
    }

    fun add(movie: Movie) {
        if (movies.any { it.id == movie.id }) throw AppErrorException(MovieCrudError.IdTaken)
        movies.add(movie)
    }

    fun nextId(): MovieId {
        val maxId = movies.maxOfOrNull { it.id.value } ?: 0
        return MovieId.of(maxId + 1)
    }

    fun update(updated: Movie) {
        val index = movies.indexOfFirst { it.id == updated.id }
        if (index == -1) {
            throw AppErrorException(MovieCrudError.NotFound)
        } else {
            val movie = movies[index]
            movie.title = updated.title
            movie.releaseDate = updated.releaseDate
            movie.status = updated.status
            movie.category = updated.category
            movie.rating = updated.rating
            movie.posterUri = updated.posterUri
        }
    }

    fun delete(movie: Movie) {
        val removed = movies.removeIf { it.id == movie.id }
        if (!removed) throw AppErrorException(MovieCrudError.NotFound)
    }

    fun clear() {
        movies.clear()
    }

}


