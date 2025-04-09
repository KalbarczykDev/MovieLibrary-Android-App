package pl.edu.pja.s27773.filmoteka.view.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
    private lateinit var summaryTextView: TextView
    private lateinit var addMovieButton: View

    private lateinit var filterCard: View
    private lateinit var filterToggleButton: View
    private var filtersVisible = false

    private lateinit var categoryMap: Map<String, Category>
    private lateinit var statusMap: Map<String, Status>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        applySystemInsets()

        initViews()
        setupToggleFilters()
        setupSpinners()
        setupRecyclerView()
        setupSpinnerListeners()
        setUpButtons()

        filterMovies()
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
        movieRecyclerView = findViewById(R.id.movie_list)
        summaryTextView = findViewById(R.id.summary_text)
        filterCard = findViewById(R.id.filter_card)
        filterToggleButton = findViewById(R.id.filter_toggle_button)

        filterCard.visibility = View.GONE
        filterToggleButton.rotation = 0f
    }

    private fun setupToggleFilters() {
        filterToggleButton.setOnClickListener {
            filtersVisible = !filtersVisible
            filterCard.visibility = if (filtersVisible) View.VISIBLE else View.GONE
            val rotation = if (filtersVisible) 180f else 0f
            filterToggleButton.animate().rotation(rotation).setDuration(200).start()
        }
    }

    private fun setupSpinners() {
        categoryMap = mapOf(
            getString(R.string.category_all) to Category.NONE,
            getString(R.string.category_movie) to Category.MOVIE,
            getString(R.string.category_series) to Category.SERIES,
            getString(R.string.category_documentary) to Category.DOCUMENTARY
        )

        statusMap = mapOf(
            getString(R.string.status_all) to Status.NONE,
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

    private fun setupSpinnerListeners() {
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterMovies()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        categorySpinner.onItemSelectedListener = listener
        statusSpinner.onItemSelectedListener = listener
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(MovieService.getAll())
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        movieRecyclerView.adapter = movieAdapter

        movieAdapter.onLongClick = { movie ->
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_delete_title))
                .setMessage(getString(R.string.confirm_delete_message, movie.title))
                .setPositiveButton(R.string.delete) { _, _ ->
                    MovieService.delete(movie)
                    filterMovies()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }
    }

    private fun filterMovies() {
        val selectedCategory = categoryMap[categorySpinner.selectedItem as String] ?: Category.NONE
        val selectedStatus = statusMap[statusSpinner.selectedItem as String] ?: Status.NONE

        val filteredMovies = MovieService.getAll().filter {
            (selectedCategory == Category.NONE || it.category == selectedCategory) &&
                    (selectedStatus == Status.NONE || it.status == selectedStatus)
        }

        movieAdapter.updateData(filteredMovies)

        summaryTextView.text = getString(R.string.summary_placeholder, filteredMovies.size)
    }

    private fun setUpButtons() {
        addMovieButton = findViewById(R.id.add_button)
        addMovieButton.setOnClickListener {
            onAddMovieClick()
        }
    }

    private fun onAddMovieClick() {
        val newMovie = MovieDto(
            id = null,
            title = "Test Movie ${System.currentTimeMillis()}",
            releaseDate = LocalDate.now(),
            category = Category.NONE,
            status = Status.NOT_WATCHED,
            rating = null,
            comment = null,
            posterUri = null
        )

        MovieService.add(newMovie)
       filterMovies()
    }


}
