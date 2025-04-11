package pl.edu.pja.s27773.filmoteka.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.MovieStatus
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto

class MovieAdapter(
    private var movies: List<MovieDto>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var onLongClick: ((MovieDto) -> Unit)? = null
    var onClick: ((MovieDto) -> Unit)? = null

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
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
            holder.rating.text = if (movie.rating % 1 == 0f) {
                "${movie.rating.toInt()}/10"
            } else {
                "${movie.rating}/10"
            }
        } else {
            holder.rating.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = movies.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMovies: List<MovieDto>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}
