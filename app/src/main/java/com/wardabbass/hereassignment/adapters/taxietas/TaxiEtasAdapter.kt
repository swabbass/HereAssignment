package com.wardabbass.hereassignment.adapters.taxietas

import android.view.LayoutInflater
import android.view.ViewGroup
import com.wardabbass.hereassignment.R
import com.wardabbass.hereassignment.adapters.common.BaseAdapter
import com.wardabbass.hereassignment.models.TaxiEta


class TaxiEtasAdapter : BaseAdapter<TaxiEta, TaxiEtaClickListener, TaxiEtaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiEtaViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.taxi_recycler_item, parent, false)
        return TaxiEtaViewHolder(view)
    }
}