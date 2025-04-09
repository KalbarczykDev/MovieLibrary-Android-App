package pl.edu.pja.s27773.filmoteka.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.Category
import pl.edu.pja.s27773.filmoteka.model.Status
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto
import pl.edu.pja.s27773.filmoteka.service.MovieService
import pl.edu.pja.s27773.filmoteka.view.adapter.MovieAdapter
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter

    private lateinit var categoryMap: Map<String, Category>
    private lateinit var statusMap: Map<String, Status>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        categorySpinner = findViewById(R.id.category_spinner)
        statusSpinner = findViewById(R.id.status_spinner)
        movieRecyclerView = findViewById(R.id.movie_list)

        setupSpinners()
        setupRecyclerView()

    }

    private fun setupSpinners() {
        // Map Category enum to string resources
        categoryMap = mapOf(
            getString(R.string.category_all) to Category.NONE,
            getString(R.string.category_movie) to Category.MOVIE,
            getString(R.string.category_series) to Category.SERIES,
            getString(R.string.category_documentary) to Category.DOCUMENTARY,

            )

        val categoryAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            categoryMap.keys.toList()
        )
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item)
        categorySpinner.adapter = categoryAdapter

        // Map Status enum to string resources
        statusMap = mapOf(
            getString(R.string.status_all) to Status.NONE,
            getString(R.string.status_watched) to Status.WATCHED,
            getString(R.string.status_not_watched) to Status.NOT_WATCHED
        )

        val statusAdapter = ArrayAdapter(
            this,
            R.layout.spinner_item,
            statusMap.keys.toList()
        )
        statusAdapter.setDropDownViewResource(R.layout.spinner_item)
        statusSpinner.adapter = statusAdapter
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(MovieService.getAll())
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieRecyclerView.adapter = movieAdapter
    }

    fun onAddMovieClick(view: View) {
        val selectedCategoryKey = categorySpinner.selectedItem as String
        val selectedStatusKey = statusSpinner.selectedItem as String

        val selectedCategory = categoryMap[selectedCategoryKey] ?: Category.MOVIE
        val selectedStatus = statusMap[selectedStatusKey] ?: Status.NOT_WATCHED

        val dto = MovieDto(
            id = null,
            title = "Test Movie ${System.currentTimeMillis()}",
            releaseDate = LocalDate.now(),
            category = selectedCategory,
            status = selectedStatus,
            rating = null,
            comment = null,
            posterUri = null
        )

        MovieService.add(dto)

        movieAdapter.updateData(MovieService.getAll())
    }
}
