package pl.edu.pja.s27773.filmoteka.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import pl.edu.pja.s27773.filmoteka.seed.MovieSeeder
import java.time.LocalDate

class MovieServiceTest {

    private lateinit var service: MovieService
    private lateinit var movie: Movie
    private lateinit var seeder: MovieSeeder

    @BeforeEach
    fun setUp() {
        service = MovieService()
        seeder = MovieSeeder
        seeder.seed()
        movie = MovieRepository.getAll().first()
    }

    @Test
    fun `should return filtered and sorted movies`() {
        val result = service.getFilteredAndSorted(MovieService.Filter(category = Category.MOVIE))
        assertTrue(result.all { it.category == Category.MOVIE })
        assertTrue(result.zipWithNext().all { it.first.releaseDate.toString() <= it.second.releaseDate.toString() })
    }

    @Test
    fun `should return correct summary count`() {
        val count = service.getSummaryCount(MovieService.Filter(status = Status.WATCHED))
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
        assertFalse(service.onMovieClick(watched))      // can't edit watched
        assertTrue(service.onMovieClick(notWatched))    // can edit not watched
    }


    @Test
    fun `should add and delete a movie`() {
        val newMovie = movie.copy(id = service.getNextId(), title = Title.of("Test Movie"), status = Status.NOT_WATCHED)
        assertTrue(service.addMovie(newMovie))
        assertTrue(service.deleteMovieById(newMovie.id))
    }

    @Test
    fun `should update a movie`() {
        val updated = movie.copy(comment = Comment.of("Updated"))
        assertTrue(service.updateMovie(updated))
        assertEquals("Updated", MovieRepository.getAll().first { it.id == movie.id }.comment.toString())
    }

    @Test
    fun `should return next unique id`() {
        val id1 = service.getNextId()
        service.addMovie(movie.copy(id = id1))
        val id2 = service.getNextId()
        assertTrue(id2.toString().toInt() > id1.toString().toInt())
    }
}