package pl.edu.pja.s27773.filmoteka.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.MovieCategory
import pl.edu.pja.s27773.filmoteka.model.MovieStatus
import pl.edu.pja.s27773.filmoteka.service.MovieService
import pl.edu.pja.s27773.filmoteka.view.adapter.MovieAdapter

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

    private lateinit var categoryMap: Map<String, MovieCategory>
    private lateinit var statusMap: Map<String, MovieStatus>
    private lateinit var addMovieLauncher: ActivityResultLauncher<Intent>


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

        addMovieLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                filterMovies()
            }
        }

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
        filterCard = findViewById(R.id.filters_card)
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
            getString(R.string.category_all) to MovieCategory.NONE,
            getString(R.string.category_movie) to MovieCategory.MOVIE,
            getString(R.string.category_series) to MovieCategory.SERIES,
            getString(R.string.category_documentary) to MovieCategory.DOCUMENTARY
        )

        statusMap = mapOf(
            getString(R.string.status_all) to MovieStatus.NONE,
            getString(R.string.status_watched) to MovieStatus.WATCHED,
            getString(R.string.status_not_watched) to MovieStatus.NOT_WATCHED
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

        movieAdapter.onClick = { movie ->

            if (movie.status == MovieStatus.WATCHED) {
                Toast.makeText(this, "${getString(R.string.already_watched)}: ${movie.title} ", Toast.LENGTH_SHORT)
                    .show()
            } else {
                movie.id?.let { movieId ->
                    val intent = Intent(
                        this,
                        AddEditMovieActivity::class.java
                    )
                    intent.putExtra("movie_id", movieId.toLong())
                    addMovieLauncher.launch(intent)
                }
            }
        }


    }

    private fun filterMovies() {
        val selectedCategory = categoryMap[categorySpinner.selectedItem as String] ?: MovieCategory.NONE
        val selectedStatus = statusMap[statusSpinner.selectedItem as String] ?: MovieStatus.NONE

        val filteredMovies = MovieService.getAll().filter {
            (selectedCategory == MovieCategory.NONE || it.category == selectedCategory) &&
                    (selectedStatus == MovieStatus.NONE || it.status == selectedStatus)
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
        val intent = Intent(this, AddEditMovieActivity::class.java)
        addMovieLauncher.launch(intent)
    }


}
