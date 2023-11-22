package com.sigma.flick.feature.myaccount.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecordsDateItemDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val count = state.itemCount
        if(position == 0){
            outRect.bottom = 12
        } else if(position == count-1){
            outRect.top = 12
        } else {
            outRect.top = 12
            outRect.bottom = 12
        }
    }
}