package capps.jotpad.viewmodel

import androidx.lifecycle.ViewModel
import capps.jotpad.room.Note
import capps.jotpad.room.NoteRepository

class HomeFragVM(private val repository: NoteRepository) : ViewModel() {
    val notes = mutableListOf<Note>()

    init {

    }


}