package ru.gb.pictureoftheday.ui.notesmanager

import ru.gb.pictureoftheday.domain.NotesEntity

interface INotesRepository {
    fun addNote(item: NotesEntity)
    fun deleteNote(pos: Int)
    fun updateNote(pos: Int)

}