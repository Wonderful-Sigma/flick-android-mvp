package com.sigma.flick.feature.event.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EventDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position == 0) {
            outRect.bottom = 15
        } else if (position == itemCount-1) {
            outRect.top = 15
        } else {
            outRect.top = 15
            outRect.bottom = 15
        }
    }
}