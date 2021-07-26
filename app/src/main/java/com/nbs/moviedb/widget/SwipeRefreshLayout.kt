package com.nbs.moviedb.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nbs.moviedb.R

class SwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SwipeRefreshLayout(context, attrs) {

    init {
        setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context, R.color.primary))
        setColorSchemeColors(ContextCompat.getColor(context, R.color.secondary))
        setDistanceToTriggerSync(300)
    }
}
