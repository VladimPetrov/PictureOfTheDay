package ru.gb.pictureoftheday.ui.otherpicture.mars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.api.Photo

class MarsRecyclerViewAdapter : RecyclerView.Adapter<MarsRecyclerViewAdapter.MainViewHolder>() {
    private var imageData: List<Photo> = listOf()

    fun setData(data: List<Photo>) {
        imageData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(imageData.get(position))
    }

    override fun getItemCount(): Int = imageData.count()

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(photo: Photo) {
            itemView.apply {

                context?.let {
                    this.findViewById<MaterialTextView>(R.id.item_photo_date_text_view).text =
                        photo.earthDate
                    this.findViewById<MaterialTextView>(R.id.item_photo_title_text_view).text =
                        "${photo.id} ${photo.rover.name}: Camera - ${photo.camera.fullName}"
                    Glide.with(it)
                        .load(photo.imgSrc.replace("http", "https", true))
                        .override(200, 300)
                        .into(this.findViewById<ImageView>(R.id.item_photo_image_view))

                }
            }
        }
    }
}