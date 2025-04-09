package pl.edu.pja.s27773.filmoteka.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pja.s27773.filmoteka.R
import pl.edu.pja.s27773.filmoteka.model.Status
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto

class MovieAdapter(private var movies: List<MovieDto>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.movie_title)
        val date: TextView = view.findViewById(R.id.movie_date)
        val status: TextView = view.findViewById(R.id.movie_status)
        val category: TextView = view.findViewById(R.id.movie_category)
        val rating: TextView = view.findViewById(R.id.movie_rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        holder.date.text = movie.releaseDate.toString()
        holder.category.text = holder.itemView.context.getString(movie.category.stringResId)

        holder.rating.text = movie.rating?.let { "$it/10" }
            ?: holder.itemView.context.getString(R.string.movie_not_rated)



        holder.status.text = holder.itemView.context.getString(movie.status.stringResId)
        val statusColorRes = if (movie.status == Status.WATCHED) {
            R.color.status_watched
        } else {
            R.color.status_unwatched
        }
        holder.status.setBackgroundResource(R.drawable.status_badge)
        holder.status.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context, statusColorRes)
    }

    override fun getItemCount(): Int = movies.size

    fun updateData(newMovies: List<MovieDto>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}