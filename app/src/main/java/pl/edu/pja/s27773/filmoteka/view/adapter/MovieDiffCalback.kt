package pl.edu.pja.s27773.filmoteka.view.adapter

import androidx.recyclerview.widget.DiffUtil
import pl.edu.pja.s27773.filmoteka.model.dto.MovieDto

/**
 * A [DiffUtil.Callback] implementation used to efficiently update the movie list in [MovieAdapter].
 *
 * Compares old and new lists to determine item identity and content changes.
 *
 * @property oldList The previous list of movies.
 * @property newList The new list of movies to be displayed.
 */
class MovieDiffCallback(
    private val oldList: List<MovieDto>,
    private val newList: List<MovieDto>
) : DiffUtil.Callback() {

    /**
     * Returns the size of the old movie list.
     */
    override fun getOldListSize() = oldList.size

    /**
     * Returns the size of the new movie list.
     */
    override fun getNewListSize() = newList.size

    /**
     * Checks whether two movie items represent the same entity (by ID).
     *
     * @param oldItemPosition Position in the old list.
     * @param newItemPosition Position in the new list.
     * @return True if items are the same; false otherwise.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    /**
     * Checks whether the contents of two movie items are equal.
     *
     * @param oldItemPosition Position in the old list.
     * @param newItemPosition Position in the new list.
     * @return True if item contents are the same; false otherwise.
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
