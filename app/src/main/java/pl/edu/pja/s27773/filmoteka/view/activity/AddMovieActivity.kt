package pl.edu.pja.s27773.filmoteka.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.*
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.service.MovieService
import java.time.LocalDate

class AddMovieActivity : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    private lateinit var statusSpinner: Spinner

    private lateinit var categoryMap: Map<String, Category>
    private lateinit var statusMap: Map<String, Status>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_movie)
        applySystemInsets()
        initViews()
        setupSpinners()
    }

    private fun applySystemInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        categorySpinner = findViewById(R.id.category_spinner)
        statusSpinner = findViewById(R.id.status_spinner)


        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<Button>(R.id.save_button).setOnClickListener {
            handleSave()
        }
    }

    private fun setupSpinners() {
        categoryMap = mapOf(
            getString(R.string.category_none) to Category.NONE,
            getString(R.string.category_movie) to Category.MOVIE,
            getString(R.string.category_series) to Category.SERIES,
            getString(R.string.category_documentary) to Category.DOCUMENTARY
        )

        statusMap = mapOf(
            getString(R.string.status_none) to Status.NONE,
            getString(R.string.status_watched) to Status.WATCHED,
            getString(R.string.status_not_watched) to Status.NOT_WATCHED
        )

        categorySpinner.adapter = ArrayAdapter(
            this, R.layout.spinner_item, categoryMap.keys.toList()
        ).apply {
            setDropDownViewResource(R.layout.spinner_item)
        }

        statusSpinner.adapter = ArrayAdapter(
            this, R.layout.spinner_item, statusMap.keys.toList()
        ).apply {
            setDropDownViewResource(R.layout.spinner_item)
        }
    }

    private fun handleSave() {

        val dto = MovieDto(
            null,
            "test", LocalDate.now(),
            Category.MOVIE,
            Status.NOT_WATCHED,
            10,
            null,
            null
        )

        try {
            MovieService.add(dto)
            Toast.makeText(this, getString(R.string.movie_added), Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
