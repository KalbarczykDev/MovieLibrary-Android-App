package pl.edu.pja.s27773.filmoteka.repository

import pl.edu.pja.s27773.filmoteka.error.MovieCrudError
import pl.edu.pja.s27773.filmoteka.model.*

//sugar code dla  singletona
object MovieRepository {
    private val movies = mutableListOf<Movie>()

    fun getAll(): List<Movie> {
        return movies.toList()
    } //immutable list

    fun getById(id: Id): Movie? {
        return movies.find { it.id == id }
    }

    fun add(movie: Movie): MovieCrudError? {
        if (movies.any { it.id == movie.id }) return MovieCrudError.ID_TAKEN
        movies.add(movie)
        return null
    }

    fun nextId(): Id {
        val maxId = movies.maxOfOrNull { it.id.value } ?: 0
        return Id.of(maxId + 1)
    }

    fun update(updated: Movie): MovieCrudError? {
        val index = movies.indexOfFirst { it.id == updated.id }
        return if (index == -1) {
            MovieCrudError.NOT_FOUND
        } else {
            movies[index] = updated
            null
        }
    }

    fun delete(movie: Movie): MovieCrudError? {
        val removed = movies.removeIf { it.id == movie.id }
        return if (!removed) MovieCrudError.NOT_FOUND else null
    }

    fun addAll(newMovies: List<Movie>): MovieCrudError? {
        newMovies.forEach {
            val error = add(it)
            if (error != null) return error
        }
        return null
    }

    fun clear() {
        movies.clear()
    }

}


