package ru.gb.pictureoftheday.ui.notesmanager

import ru.gb.pictureoftheday.domain.NotesEntity

interface INotesRepository {
    fun addNote(pos: Int, item: NotesEntity)
    fun deleteNote(pos: Int)
    fun updateNote(pos: Int, item: NotesEntity)
    fun moveNote(from: Int, to: Int)
}