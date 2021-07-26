package com.nbs.moviedb.presentation.itemdecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.R

class ListItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapterPosition = parent.getChildAdapterPosition(view)
        if (adapterPosition == RecyclerView.NO_POSITION) return

        val horizontalSpace = view.resources.getDimensionPixelSize(R.dimen.keyline_6)
        val verticalSpace = view.resources.getDimensionPixelSize(R.dimen.keyline_5)
        val bottomSpace = if (adapterPosition != parent.adapter?.itemCount) verticalSpace else 0

        outRect.apply {
            left = horizontalSpace
            right = horizontalSpace
            bottom = bottomSpace
        }
    }
}
