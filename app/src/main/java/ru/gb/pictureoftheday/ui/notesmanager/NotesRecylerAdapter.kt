package ru.gb.pictureoftheday.ui.notesmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.domain.NotesEntity
import ru.gb.pictureoftheday.visiblyIf
import java.util.*

class NotesRecylerAdapter(
    val itemRemoved: ((pos: Int) -> Unit)? = null,
    val itemAdd: ((pos: Int) -> Unit)? = null,
    val itemMove: ((from: Int, to: Int) -> Unit)? = null
) : ListAdapter<NotesEntity, NotesRecylerAdapter.NotesViewHolder>(object :
    DiffUtil.ItemCallback<NotesEntity>() {
    override fun areItemsTheSame(oldItem: NotesEntity, newItem: NotesEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NotesEntity, newItem: NotesEntity): Boolean =
        oldItem == newItem
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder =
        NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_note, parent, false) as View
        )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    inner class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date = itemView.findViewById<TextView>(R.id.recycle_item_note_date)
        val title = itemView.findViewById<TextView>(R.id.recycle_item_note_title)
        val buttonUp = itemView.findViewById<ImageView>(R.id.recycle_item_note_button_up)
        val buttonDown = itemView.findViewById<ImageView>(R.id.recycle_item_note_button_down)
        val buttonDelete = itemView.findViewById<ImageView>(R.id.recycle_item_note_button_delete)
        val buttonAdd = itemView.findViewById<ImageView>(R.id.recycle_item_note_button_add)
        fun bind(note: NotesEntity) {
            date.text = note.date
            title.text = note.title
            buttonUp.visiblyIf { adapterPosition != 0 }
            buttonDown.visiblyIf { adapterPosition != currentList.size - 1 }
        }

        init {

            buttonDelete.setOnClickListener {
                itemRemoved?.invoke(adapterPosition)
            }
            buttonAdd.setOnClickListener {
                itemAdd?.invoke(adapterPosition)
            }
            buttonUp.setOnClickListener {
                buttonUp.visiblyIf { adapterPosition != 1 }
                buttonDown.visiblyIf { true }
                itemMove?.invoke(adapterPosition, adapterPosition - 1)
            }
            buttonDown.setOnClickListener {
                buttonDown.visiblyIf { adapterPosition != currentList.size - 2 }
                buttonUp.visiblyIf { true }
                itemMove?.invoke(adapterPosition, adapterPosition + 1)
            }
        }
    }
}