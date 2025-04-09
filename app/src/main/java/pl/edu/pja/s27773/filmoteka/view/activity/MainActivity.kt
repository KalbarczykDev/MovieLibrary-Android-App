package pl.edu.pja.s27773.filmoteka.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.Category
import pl.edu.pja.s27773.filmoteka.model.Status
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.service.MovieService
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun onAddMovieClick(view: View) {

        val dto = MovieDto(null, "test", LocalDate.now(), Category.MOVIE, Status.NOT_WATCHED, null, null, null)

        MovieService.add(dto)

        Log.d("MainActivity", "Movie added: $dto")
    }
}