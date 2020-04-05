package studio.honidot.linetv

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import studio.honidot.linetv.data.Drama
import studio.honidot.linetv.drama.DramaAdapter
import studio.honidot.linetv.network.LoadApiStatus
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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

@BindingAdapter("certainDecimalPlace")
fun bindFloatNumber(txtView: TextView, number: Float?) {
    txtView.text = "%.2f".format(number)
}

@BindingAdapter("shrankCreatedTime")
fun bindTime(txtView: TextView, originTime: String?) {

    val localDateTime =
        ZonedDateTime.parse(originTime)
    val formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd")
    txtView.text = formatter.format(localDateTime)
}

@BindingAdapter("addComma")
fun bindComma(txtView: TextView, number: Long?) {
    txtView.text = "%,d".format(number)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        GlideApp.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
            )
            .into(imgView)
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