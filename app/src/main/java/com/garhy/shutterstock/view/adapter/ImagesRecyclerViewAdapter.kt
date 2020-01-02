package com.garhy.shutterstock.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garhy.shutterstock.R
import com.garhy.shutterstock.model.ImageModel
import com.garhy.shutterstock.view.OnItemClickListener
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImagesRecyclerViewAdapter @Inject constructor(
    val context: Context?,
    private val picasso: Picasso
) :
    RecyclerView.Adapter<ImagesRecyclerViewAdapter.ViewHolder>() {
    lateinit var images: ArrayList<ImageModel>

    lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageModel = images[position]
        picasso.load(imageModel.assets?.preview?.url).placeholder(R.mipmap.ic_launcher)
            .error(R.drawable.broken_image).into(holder.imageView)
        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(imageModel.assets?.preview?.url) }
    }

    fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
        if (recyclerView.adapter?.itemCount != 0) {
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter?.itemCount?.minus(
                1
            )
        }
        return false
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_image_view)

    }
}