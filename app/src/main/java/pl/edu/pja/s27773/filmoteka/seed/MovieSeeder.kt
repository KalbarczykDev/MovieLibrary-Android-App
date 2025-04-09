package pl.edu.pja.s27773.filmoteka.seed

import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.repository.MovieRepository
import java.time.LocalDate

object MovieSeeder {

    fun seed() {
        MovieRepository.clear()

        var idCounter = 1;
        fun nextSeedId() = Id.of(idCounter++)

        try {
            val movies = listOf(
                Movie(
                    nextSeedId(), Title.of("Inception"), ReleaseDate.of(LocalDate.of(2010, 7, 16)),
                    Category.MOVIE, Status.WATCHED, Rating.of(7), Comment.of("Surreal!"), null
                ),
                Movie(
                    nextSeedId(), Title.of("Planet Earth"), ReleaseDate.of(LocalDate.of(2006, 3, 5)),
                    Category.DOCUMENTARY, Status.NOT_WATCHED, null, null, null
                ),
                Movie(
                    nextSeedId(), Title.of("Breaking Bad"), ReleaseDate.of(LocalDate.of(2008, 1, 20)),
                    Category.SERIES, Status.WATCHED, Rating.of(8), Comment.of("BEST SERIES OF ALL TIME!"), null
                ),
                Movie(
                    nextSeedId(), Title.of("Game Of Thrones"), ReleaseDate.of(LocalDate.of(2011, 4, 17)),
                    Category.SERIES, Status.NOT_WATCHED, null, null, null
                ),
                Movie(
                    nextSeedId(), Title.of("The Matrix"), ReleaseDate.of(LocalDate.of(1999, 3, 31)),
                    Category.MOVIE, Status.WATCHED, Rating.of(7), Comment.of("A sci-fi masterpiece!"), null
                ),
                Movie(
                    nextSeedId(), Title.of("The Godfather"), ReleaseDate.of(LocalDate.of(1972, 3, 24)),
                    Category.MOVIE, Status.WATCHED, Rating.of(10), Comment.of("A timeless classic."), null
                ),
                Movie(
                    nextSeedId(), Title.of("Interstellar"), ReleaseDate.of(LocalDate.of(2014, 11, 7)),
                    Category.MOVIE, Status.WATCHED, Rating.of(7), Comment.of("Mind-bending and emotional."), null
                ),
                Movie(
                    nextSeedId(), Title.of("Chernobyl"), ReleaseDate.of(LocalDate.of(2019, 5, 6)),
                    Category.SERIES, Status.WATCHED, Rating.of(8), Comment.of("Gripping and haunting."), null
                ),
                Movie(
                    nextSeedId(), Title.of("The Dark Knight"), ReleaseDate.of(LocalDate.of(2008, 7, 18)),
                    Category.MOVIE, Status.WATCHED, Rating.of(7), Comment.of("Best superhero movie ever!"), null
                ),
                Movie(
                    nextSeedId(), Title.of("Schindler's List"), ReleaseDate.of(LocalDate.of(1993, 12, 15)),
                    Category.MOVIE, Status.WATCHED, Rating.of(10), Comment.of("Heartbreaking and powerful."), null
                ),
                Movie(
                    nextSeedId(), Title.of("Snow White"),
                    ReleaseDate.of(
                        LocalDate.of(2025, 4, 2)
                    ),
                    Category.MOVIE, Status.WATCHED, Rating.of(1), Comment.of("Disappointing"), null
                ),
            )

            MovieRepository.addAll(movies)

        } catch (e: Exception) {
            println("Error seeding movies: ${e.message}")
        }


    }

}