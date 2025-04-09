package pl.edu.pja.s27773.filmoteka.repository

import pl.edu.pja.s27773.filmoteka.model.*
import java.time.LocalDate

//object to singleton
object MovieRepository {
    private val movies = mutableListOf<Movie>()

    fun loadSampleData() {
        movies.clear()
        movies.addAll(
            listOf(
                Movie(
                    Id.of(1), Title.of("Incepcja"), ReleaseDate.of(LocalDate.of(2010, 7, 16)),
                    Category.MOVIE, Status.WATCHED, Rating.of(9), Comment.of("Mega!"), null
                ),
                Movie(
                    Id.of(2), Title.of("Planet Earth"), ReleaseDate.of(LocalDate.of(2006, 3, 5)),
                    Category.DOCUMENTARY, Status.NOT_WATCHED, null, null, null
                )
            )
        )
    }

    fun add(movie: Movie) {
        require(movies.none { it.id == movie.id }) { "Movie with this ID already exists." }
        movies.add(movie)
    }

    fun remove(movie: Movie) {
        movies.removeIf { it.id == movie.id }
    }

    fun nextId(): Id {
        val maxId = movies.maxOfOrNull { it.id.value } ?: 0
        return Id.of(maxId + 1)
    }

    fun getAll(): List<Movie> = movies.toList()
}
