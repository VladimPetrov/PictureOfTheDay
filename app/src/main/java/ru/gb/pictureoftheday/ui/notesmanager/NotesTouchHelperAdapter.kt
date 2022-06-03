package ru.gb.pictureoftheday.ui.notesmanager

interface NotesTouchHelperAdapter {
    fun onNoteMoved (from: Int, to:Int)
    fun onNoteDismissed(pos:Int)
}