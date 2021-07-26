package com.nbs.moviedb.presentation.itemdecorations

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nbs.moviedb.R

class GenreItemDecoration(
    private val divider: Drawable
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapterPosition = parent.getChildAdapterPosition(view)
        if (adapterPosition == RecyclerView.NO_POSITION) return
        val space = view.resources.getDimensionPixelSize(R.dimen.keyline_2)

        outRect.apply {
            right = (divider.intrinsicWidth + space)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount.minus(1)) {
            val child = parent.getChildAt(i)

            val space = parent.resources.getDimensionPixelSize(R.dimen.keyline_2)

            val left = child.right + (space / 2)
            val right = left + divider.intrinsicWidth
            val top = child.height / 2
            val bottom = top + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }

    }
}
