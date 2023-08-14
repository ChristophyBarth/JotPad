package capps.jotpad.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import capps.jotpad.room.NoteRepository
import capps.jotpad.viewmodel.HomeFragVM

class HomeFragVMFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFragVM::class.java)){
            return HomeFragVM(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}