package pl.edu.pja.s27773.filmoteka.repository

import org.junit.jupiter.api.*
import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.error.MovieCrudError
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieRepositoryTest {

    private lateinit var movie: Movie

    @BeforeEach
    fun setUp() {
        MovieRepository.clear()
        movie = Movie(
            id = MovieRepository.nextId(),
            title = Title.of("Inception"),
            releaseDate = ReleaseDate.of(LocalDate.of(2010, 7, 16)),
            category = Category.MOVIE,
            status = Status.NOT_WATCHED
        )
    }

    @Test
    fun `should add movie successfully`() {
        val result = MovieRepository.add(movie)
        assertNull(result)
        assertEquals(1, MovieRepository.getAll().size)
    }

    @Test
    fun `should not add movie with duplicate id`() {
        MovieRepository.add(movie)
        val result = MovieRepository.add(movie) // same ID
        assertEquals(MovieCrudError.ID_TAKEN, result)
    }

    @Test
    fun `should update existing movie`() {
        MovieRepository.add(movie)
        val updated = movie.copy(status = Status.WATCHED)
        val result = MovieRepository.update(updated)
        assertNull(result)
        assertEquals(Status.WATCHED, MovieRepository.getAll().first().status)
    }

    @Test
    fun `should return NOT_FOUND when updating non-existing movie`() {
        val result = MovieRepository.update(movie)
        assertEquals(MovieCrudError.NOT_FOUND, result)
    }

    @Test
    fun `should delete movie by id`() {
        MovieRepository.add(movie)
        val result = MovieRepository.deleteById(movie.id)
        assertNull(result)
        assertEquals(0, MovieRepository.getAll().size)
    }

    @Test
    fun `should return NOT_FOUND when deleting non-existing movie`() {
        val result = MovieRepository.deleteById(movie.id)
        assertEquals(MovieCrudError.NOT_FOUND, result)
    }

    @Test
    fun `should add all movies`() {
        val movie2 = movie.copy(id = Id.of(movie.id.toString().toInt() + 1), title = Title.of("Matrix"))
        val result = MovieRepository.addAll(listOf(movie, movie2))
        assertNull(result)
        assertEquals(2, MovieRepository.getAll().size)
    }


    @Test
    fun `should fail addAll if one movie has a duplicate ID`() {
        MovieRepository.add(movie)
        val duplicate = movie.copy(title = Title.of("Duplicate"))
        val result = MovieRepository.addAll(listOf(movie, duplicate))
        assertEquals(MovieCrudError.ID_TAKEN, result)
    }
}
