package com.nbs.moviedb.presentation.itemdecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.nbs.moviedb.R

class HorizontalItemDecoration : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val adapterPosition = parent.getChildAdapterPosition(view)

        if (adapterPosition == RecyclerView.NO_POSITION) return
        val adapterStartPosition = 0
        val adapterSize = parent.adapter?.itemCount?.minus(1) ?: 0

        val itemSpace = view.resources.getDimension(R.dimen.keyline_2).toInt()
        val containerSpace = view.resources.getDimension(R.dimen.keyline_6).toInt()

        when (adapterPosition) {
            adapterStartPosition -> {
                // set first item start and end space
                outRect.left = containerSpace
                outRect.right = itemSpace
            }
            adapterSize -> {
                // set last item start and end space
                outRect.right = containerSpace
            }
            else -> {
                outRect.right = itemSpace
            }
        }
    }
}