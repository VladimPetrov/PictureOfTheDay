package ru.gb.pictureoftheday.ui.marsroverpicture

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.textview.MaterialTextView
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.api.MarsRoverResponse
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
    ): MarsRecyclerViewAdapter.MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MarsRecyclerViewAdapter.MainViewHolder, position: Int) {
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
                    this.findViewById<ImageView>(R.id.item_photo_image_view).load(photo.imgSrc)
                }
            }
        }
    }
}