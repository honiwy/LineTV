package studio.honidot.linetv

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.honidot.linetv.data.Drama
import studio.honidot.linetv.drama.DramaAdapter
import studio.honidot.linetv.network.LoadApiStatus

@BindingAdapter("dramas")
fun bindRecyclerViewWithDramas(recyclerView: RecyclerView, dramas: List<Drama>?) {
    dramas?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is DramaAdapter -> submitList(it)
            }
        }
    }
}

/**
 * According to [LoadApiStatus] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiStatus")
fun bindApiStatus(view: ProgressBar, status: LoadApiStatus?) {
    when (status) {
        LoadApiStatus.LOADING -> view.visibility = View.VISIBLE
        LoadApiStatus.DONE, LoadApiStatus.ERROR -> view.visibility = View.GONE
    }
}

/**
 * According to [message] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiErrorMessage")
fun bindApiErrorMessage(view: TextView, message: String?) {
    when (message) {
        null, "" -> {
            view.visibility = View.GONE
        }
        else -> {
            view.text = message
            view.visibility = View.VISIBLE
        }
    }
}