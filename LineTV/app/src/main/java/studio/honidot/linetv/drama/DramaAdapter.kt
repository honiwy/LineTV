package studio.honidot.linetv.drama

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.honidot.linetv.data.Drama
import studio.honidot.linetv.databinding.ItemDramaBriefBinding

class DramaAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Drama, DramaAdapter.DramaViewHolder>(
        DiffCallback
    ) {

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Drama]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Drama]
     */
    class OnClickListener(val clickListener: (drama: Drama) -> Unit) {
        fun onClick(drama: Drama) = clickListener(drama)
    }

    class DramaViewHolder(private var binding: ItemDramaBriefBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(drama: Drama, onClickListener: OnClickListener) {
            binding.drama = drama
            binding.root.setOnClickListener { onClickListener.onClick(drama) }
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Drama>() {
        override fun areItemsTheSame(oldItem: Drama, newItem: Drama): Boolean {
            return oldItem.dramaId == newItem.dramaId
        }

        override fun areContentsTheSame(oldItem: Drama, newItem: Drama): Boolean {
            return (oldItem.thumb == newItem.thumb && 
                    oldItem.createdAt == newItem.createdAt &&
                    oldItem.rating == newItem.rating)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DramaViewHolder {
        return DramaViewHolder(
            ItemDramaBriefBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: DramaViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }

}