package ru.gb.pictureoftheday.ui.notesmanager

import ru.gb.pictureoftheday.domain.NotesEntity
import ru.gb.pictureoftheday.getStrDatePlus

class NotesRepositoryLocalImpl:INotesRepository {
    private val listItems: MutableList<NotesEntity> = createDate()

    private fun createDate():MutableList<NotesEntity> {
        val data:MutableList<NotesEntity> = mutableListOf()
        for ( i in 0..9) {
            data.add(
                NotesEntity(
                    date = getStrDatePlus(i),
                    title = " Заметка $i",
                    text = getStrDatePlus(i) + " Заметка $i"
                )
            )
        }
        return data
    }

    fun getData():List<NotesEntity> = listItems.toMutableList()

    override fun addNote(item: NotesEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteNote(pos: Int) {
        listItems.removeAt(pos)
    }

    override fun updateNote(pos: Int) {
        TODO("Not yet implemented")
    }
}