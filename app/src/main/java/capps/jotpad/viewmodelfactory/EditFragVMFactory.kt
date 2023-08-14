package capps.jotpad.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import capps.jotpad.room.Note
import capps.jotpad.room.NoteRepository
import capps.jotpad.viewmodel.EditFragVM

class EditFragVMFactory(private val editing: Boolean, private val note: Note?, private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditFragVM::class.java)){
            return EditFragVM(editing, note, repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}