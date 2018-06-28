package com.wardabbass.hereassignment.adapters.taxietas

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.wardabbass.hereassignment.R
import com.wardabbass.hereassignment.adapters.common.BaseViewHolder
import com.wardabbass.hereassignment.models.TaxiEta


class TaxiEtaViewHolder(view: View) : BaseViewHolder<TaxiEta, TaxiEtaClickListener>(view) {


    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    val etaTextView: TextView = itemView.findViewById(R.id.etaTextView)
    override fun onBind(item: TaxiEta, listener: TaxiEtaClickListener?) {

        //load thumbnail
        Glide.with(imageView).load(item.imageUrl)
                .apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_local_taxi_black_24dp)
                        .error(R.drawable.ic_local_taxi_black_24dp).diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(imageView)


        titleTextView.text = item.name
        etaTextView.text = item.dateDifFromNow
        itemView.setOnClickListener {
            listener?.onItemClicked(item, adapterPosition, itemView)
        }
    }

}