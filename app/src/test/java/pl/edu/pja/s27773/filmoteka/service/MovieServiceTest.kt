package pl.edu.pja.s27773.filmoteka.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import pl.edu.pja.s27773.filmoteka.seed.MovieSeeder
import java.time.LocalDate

class MovieServiceTest {

    private lateinit var movie: Movie

    @BeforeEach
    fun setUp() {
        MovieSeeder.seed()
        movie = MovieRepository.getAll().first()
    }

    @Test
    fun `should return filtered and sorted movies`() {
        val result = MovieService.getFilteredAndSorted(MovieService.Filter(category = Category.MOVIE))
        assertTrue(result.all { it.category == Category.MOVIE })
        assertTrue(result.zipWithNext().all { it.first.releaseDate.toString() <= it.second.releaseDate.toString() })
    }

    @Test
    fun `should return correct summary count`() {
        val count = MovieService.getSummaryCount(MovieService.Filter(status = Status.WATCHED))
        assertEquals(1, count)
    }

    @Test
    fun `should determine if movie can be edited`() {
        // Arrange
        MovieRepository.clear()
        val watched = Movie(
            id = Id.of(1),
            title = Title.of("Watched"),
            releaseDate = ReleaseDate.of(LocalDate.of(2020, 1, 1)),
            category = Category.MOVIE,
            status = Status.WATCHED
        )
        val notWatched = Movie(
            id = Id.of(2),
            title = Title.of("Not Watched"),
            releaseDate = ReleaseDate.of(LocalDate.of(2021, 1, 1)),
            category = Category.MOVIE,
            status = Status.NOT_WATCHED
        )
        MovieRepository.add(watched)
        MovieRepository.add(notWatched)

        // Act & Assert
        assertFalse(MovieService.onMovieClick(watched))      // can't edit watched
        assertTrue(MovieService.onMovieClick(notWatched))    // can edit not watched
    }


    @Test
    fun `should add and delete a movie`() {
        val newMovie = movie.copy(id = MovieService.getNextId(), title = Title.of("Test Movie"), status = Status.NOT_WATCHED)
        assertTrue(MovieService.addMovie(newMovie))
        assertTrue(MovieService.deleteMovieById(newMovie.id))
    }

    @Test
    fun `should update a movie`() {
        val updated = movie.copy(comment = Comment.of("Updated"))
        assertTrue(MovieService.updateMovie(updated))
        assertEquals("Updated", MovieRepository.getAll().first { it.id == movie.id }.comment.toString())
    }

    @Test
    fun `should return next unique id`() {
        val id1 = MovieService.getNextId()
        MovieService.addMovie(movie.copy(id = id1))
        val id2 = MovieService.getNextId()
        assertTrue(id2.toString().toInt() > id1.toString().toInt())
    }
}