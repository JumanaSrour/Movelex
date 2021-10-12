package com.example.newmovlex.utils

import android.text.Layout
import android.widget.AbsListView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationLinearScrollListener : RecyclerView.OnScrollListener{
        lateinit var layoutManager: RecyclerView.LayoutManager
        private var userScrolled = false
        private var VERTICAL_VIEW = 0
        private var GRID_VIEW = 1
        private var defaultView = VERTICAL_VIEW



    constructor(layoutManager: RecyclerView.LayoutManager) {
        this.layoutManager = layoutManager
    }

    open fun PaginationScrollListener(
        mLayoutManager: RecyclerView.LayoutManager,
        defaultView: Int,
    ) {
        this.defaultView = defaultView
        layoutManager = mLayoutManager
//        if (defaultView == GRID_VIEW) {
//
//        }
    }
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        userScrolled = newState != AbsListView.OnScrollListener.SCROLL_STATE_IDLE
    }


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView!!, dx, dy)
//        onScrolledList(dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        var firstVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (defaultView == GRID_VIEW) {
            firstVisibleItemPosition =
                (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
        }


        if (!_isLoading()/* && userScrolled */ && !_isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }

        val topRowVerticalPosition = if(recyclerView == null || recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
        val firstVisibleItem: Int
        if (defaultView == GRID_VIEW) {
            val gridLayoutManager = recyclerView!!.layoutManager as GridLayoutManager
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()
        } else{
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
        }
        setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0)
    }

//    abstract fun onScrolledList(dx: Int, dy: Int)
    protected abstract fun loadMoreItems()
    protected abstract fun setEnabled(refresh: Boolean)
    abstract fun _isLastPage(): Boolean
    abstract fun _isLoading(): Boolean

}