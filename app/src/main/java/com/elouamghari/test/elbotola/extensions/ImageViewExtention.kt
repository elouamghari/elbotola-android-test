package com.elouamghari.test.elbotola.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade


fun ImageView.setImageLink(link: String){
    Glide.with(context)
        .load(link)
        .transition(withCrossFade())
        .into(this)
}