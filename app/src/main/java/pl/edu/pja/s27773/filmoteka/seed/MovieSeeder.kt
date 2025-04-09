package pl.edu.pja.s27773.filmoteka.seed

import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository.nextId
import java.time.LocalDate

object MovieSeeder {

    fun seed() {
        MovieRepository.clear()

        val movies = listOf(
            Movie(
                nextId(), Title.of("Inception"), ReleaseDate.of(LocalDate.of(2010, 7, 16)),
                Category.MOVIE, Status.WATCHED, Rating.of(9), Comment.of("Surreal!"), null
            ),
            Movie(
                nextId(), Title.of("Planet Earth"), ReleaseDate.of(LocalDate.of(2006, 3, 5)),
                Category.DOCUMENTARY, Status.NOT_WATCHED, null, null, null
            ),
            Movie(
                nextId(), Title.of("Breaking Bad"), ReleaseDate.of(LocalDate.of(2008, 1, 20)),
                Category.SERIES, Status.WATCHED, Rating.of(10), Comment.of("BEST SERIES OF ALL TIME!"), null
            ),
            Movie(
                nextId(), Title.of("Game Of Thrones"), ReleaseDate.of(LocalDate.of(2011, 4, 17)),
                Category.SERIES, Status.NOT_WATCHED, null, null, null
            ),
            Movie(
                nextId(), Title.of("The Matrix"), ReleaseDate.of(LocalDate.of(1999, 3, 31)),
                Category.MOVIE, Status.WATCHED, Rating.of(10), Comment.of("A sci-fi masterpiece!"), null
            ),
            Movie(
                nextId(), Title.of("The Godfather"), ReleaseDate.of(LocalDate.of(1972, 3, 24)),
                Category.MOVIE, Status.WATCHED, Rating.of(10), Comment.of("A timeless classic."), null
            ),
            Movie(
                nextId(), Title.of("Interstellar"), ReleaseDate.of(LocalDate.of(2014, 11, 7)),
                Category.MOVIE, Status.WATCHED, Rating.of(9), Comment.of("Mind-bending and emotional."), null
            ),
            Movie(
                nextId(), Title.of("Chernobyl"), ReleaseDate.of(LocalDate.of(2019, 5, 6)),
                Category.SERIES, Status.WATCHED, Rating.of(10), Comment.of("Gripping and haunting."), null
            ),
            Movie(
                nextId(), Title.of("The Dark Knight"), ReleaseDate.of(LocalDate.of(2008, 7, 18)),
                Category.MOVIE, Status.WATCHED, Rating.of(10), Comment.of("Best superhero movie ever!"), null
            ),
            Movie(
                nextId(), Title.of("Schindler's List"), ReleaseDate.of(LocalDate.of(1993, 12, 15)),
                Category.MOVIE, Status.WATCHED, Rating.of(10), Comment.of("Heartbreaking and powerful."), null
            )
        )

        val error = MovieRepository.addAll(movies)
        if (error != null) {
            println("Error seeding movies: $error")
        } else {
            println("Movies seeded successfully")
        }
    }

}