package ru.gb.pictureoftheday.ui.notesmanager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import ru.gb.pictureoftheday.R
import ru.gb.pictureoftheday.databinding.FragmentNotesManagerBinding

class NotesManagerFragment : Fragment(R.layout.fragment_notes_manager) {
    private lateinit var binding: FragmentNotesManagerBinding
    private lateinit var adapter : NotesRecylerAdapter
    private val repository = NotesRepositoryLocalImpl()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesManagerBinding.bind(view)
        adapter = NotesRecylerAdapter({
            repository.deleteNote(it)
            adapter.submitList(ArrayList(repository.getData()))
        })
        binding.fragmentNotesManagerRecycler.adapter = adapter
        adapter.submitList(repository.getData())
        ItemTouchHelper(NotesTouchHelperCallBack({
            adapter.itemRemoved?.invoke(it)
        }, { from, to ->
            with(adapter) {
                itemsMoved(from, to)
                notifyItemChanged(from)
                notifyItemChanged(to)
            }
        })).attachToRecyclerView(binding.fragmentNotesManagerRecycler)
    }

}