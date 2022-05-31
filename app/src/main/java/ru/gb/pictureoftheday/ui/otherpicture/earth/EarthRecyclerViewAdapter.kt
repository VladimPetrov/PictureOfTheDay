package ru.gb.pictureoftheday.ui.otherpicture.earth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.textview.MaterialTextView
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.api.EarthPhoto

class EarthRecyclerViewAdapter : RecyclerView.Adapter<EarthRecyclerViewAdapter.MainViewHolder>() {
    private var imageData: List<EarthPhoto> = listOf()

    fun setData(data: List<EarthPhoto>) {
        imageData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EarthRecyclerViewAdapter.MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: EarthRecyclerViewAdapter.MainViewHolder, position: Int) {
        holder.bind(imageData.get(position))
    }

    override fun getItemCount(): Int = imageData.count()
    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(photo: EarthPhoto) {
            itemView.apply {

                context?.let {
                    this.findViewById<MaterialTextView>(R.id.item_photo_date_text_view).text =
                        photo.date
                    this.findViewById<MaterialTextView>(R.id.item_photo_title_text_view).text =
                        photo.caption
                    this.findViewById<ImageView>(R.id.item_photo_image_view).load(
                        "https://epic.gsfc.nasa.gov/archive/enhanced/2021/05/10/png/" + photo.image + ".png"
                    )
                }
            }
        }
    }
}