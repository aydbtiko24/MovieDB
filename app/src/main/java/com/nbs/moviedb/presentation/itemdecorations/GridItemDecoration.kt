package com.nbs.moviedb.presentation.itemdecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.R

class GridItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapterPosition = parent.getChildAdapterPosition(view)

        if (adapterPosition == RecyclerView.NO_POSITION) return
        val adapterStartPosition = adapterPosition <= 1
        val itemSpace = view.resources.getDimensionPixelSize(R.dimen.keyline_5)
        val layoutSpace = view.resources.getDimensionPixelSize(R.dimen.keyline_6)
        // adding top space
        if (!adapterStartPosition) {
            outRect.top = itemSpace
        }
        // adding horizontal space space
        val horizontalSpace = itemSpace / 2
        if (adapterPosition % 2 == 0) {
            outRect.right = horizontalSpace
            outRect.left = layoutSpace
        } else {
            outRect.left = horizontalSpace
            outRect.right = layoutSpace
        }
    }
}
