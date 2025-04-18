package pl.edu.pja.s27773.filmoteka.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.MovieStatus
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto

/**
 * RecyclerView adapter for displaying a list of movies.
 *
 * Supports click and long-click interactions and efficiently updates its content using [DiffUtil].
 *
 * @property movies The list of movies to display.
 */
class MovieAdapter(
    private var movies: List<MovieDto>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    /**
     * Callback invoked when a movie item is long-pressed.
     */
    var onLongClick: ((MovieDto) -> Unit)? = null

    /**
     * Callback invoked when a movie item is tapped.
     */
    var onClick: ((MovieDto) -> Unit)? = null

    /**
     * ViewHolder for movie items.
     *
     * Holds references to views for displaying movie attributes and
     * handles click/long-click interactions.
     */
    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.movie_title)
        val date: TextView = view.findViewById(R.id.movie_date)
        val status: TextView = view.findViewById(R.id.movie_status)
        val category: TextView = view.findViewById(R.id.movie_category)
        val rating: TextView = view.findViewById(R.id.movie_rating)
        val poster: ImageView = view.findViewById(R.id.movie_poster)
        val posterTitle: TextView = view.findViewById(R.id.movie_poster_title)

        init {
            view.setOnClickListener { onClick?.invoke(movies[adapterPosition]) }
            view.setOnLongClickListener {
                onLongClick?.invoke(movies[adapterPosition])
                true
            }
        }
    }

    /**
     * Inflates the layout for an individual movie item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * Binds movie data to the ViewHolder.
     *
     * @param holder The holder to bind data to.
     * @param position The position of the item in the data set.
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        val context = holder.itemView.context

        holder.title.text = movie.title
        holder.date.text = movie.releaseDate.toString()
        holder.category.text = context.getString(movie.category.stringResId)
        holder.posterTitle.text = movie.title

        // Poster or placeholder
        if (movie.posterUri != null) {
            holder.poster.setImageURI(movie.posterUri)
            holder.posterTitle.visibility = View.GONE
        } else {
            holder.poster.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.image_placeholder))
            holder.posterTitle.visibility = View.VISIBLE
        }

        // Status
        holder.status.text = context.getString(movie.status.stringResId)
        holder.status.setBackgroundResource(R.drawable.status_badge)
        holder.status.backgroundTintList = ContextCompat.getColorStateList(
            context,
            if (movie.status == MovieStatus.WATCHED) R.color.status_watched else R.color.status_unwatched
        )
        holder.status.visibility = if (movie.status == MovieStatus.NOT_WATCHED) View.GONE else View.VISIBLE

        // Rating
        if (movie.status == MovieStatus.WATCHED && movie.rating != null) {
            holder.rating.visibility = View.VISIBLE
            holder.rating.text = if (movie.rating % 1 == 0f) {
                "${movie.rating.toInt()}/5"
            } else {
                "${movie.rating}/5"
            }
        } else {
            holder.rating.visibility = View.GONE
        }
    }

    /**
     * Returns the number of items in the list.
     */
    override fun getItemCount(): Int = movies.size


    /**
     * Updates the adapter's dataset and uses [DiffUtil] to efficiently notify changes.
     *
     * @param newMovies The new list of movies to display.
     */
    fun updateData(newMovies: List<MovieDto>) {
        val diffCallback = MovieDiffCallback(movies, newMovies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        movies = newMovies
        diffResult.dispatchUpdatesTo(this)
    }

}
