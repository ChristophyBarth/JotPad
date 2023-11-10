package capps.jotpad.viewmodel

import androidx.lifecycle.ViewModel
import capps.jotpad.room.NoteRepository

class HomeFragVM(repository: NoteRepository) : ViewModel() {
    val notes = repository.notes
}