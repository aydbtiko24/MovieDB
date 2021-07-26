package com.nbs.moviedb.presentation.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.nbs.moviedb.R

/**Binding adapter to load an image from url*/
@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    imageUrl?.let { url ->
        load(url) {
            crossfade(enable = true)
            error(android.R.color.darker_gray)
        }
    }
}

@BindingAdapter("roundedImageUrl")
fun ImageView.setRoundedImageUrl(imageUrl: String?) {
    imageUrl?.let { url ->
        load(url) {
            crossfade(enable = true)
            error(android.R.color.darker_gray)
            val roundedSize = resources.getDimension(R.dimen.rounded_size)
            transformations(RoundedCornersTransformation(roundedSize))
        }
    }
}

@BindingAdapter("circleImageUrl")
fun ImageView.setCircleImageUrl(imageUrl: String?) {
    imageUrl?.let { url ->
        load(url) {
            crossfade(enable = true)
            error(R.drawable.ic_avatar)
            transformations(CircleCropTransformation())
        }
    }
}

/**Binding adapter to set view's visibility*/
@BindingAdapter("viewVisible")
fun View.setVisible(viewVisible: Boolean) {
    isVisible = viewVisible
}

/**Binding adapter to set text view's text from html text*/
@BindingAdapter("htmlText")
fun TextView.setHtmlText(text: String?) {
    text?.let {
        this.text = HtmlCompat.fromHtml(
            it,
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
    }
}
