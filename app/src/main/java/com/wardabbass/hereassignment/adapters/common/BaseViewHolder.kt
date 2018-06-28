package com.wardabbass.hereassignment.adapters.common

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<D,L : BaseRecyclerItemClickListener>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun onBind(item:D, listener:L?)
}