package com.example.productcrud.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.productcrud.databinding.EmptyStateBinding

class EmptyStateObserver(
    private val emptyStateBinding: EmptyStateBinding,
    private val recyclerView: RecyclerView
) :
    RecyclerView.AdapterDataObserver() {
    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        super.onItemRangeInserted(positionStart, itemCount)
        checkIfEmpty()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        super.onItemRangeRemoved(positionStart, itemCount)
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        val isEmpty = recyclerView.adapter!!.itemCount == 0
        emptyStateBinding.root.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}