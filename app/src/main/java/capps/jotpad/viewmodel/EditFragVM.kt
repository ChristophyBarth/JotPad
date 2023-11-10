package capps.jotpad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import capps.jotpad.others.Event
import capps.jotpad.others.Object
import capps.jotpad.room.Note
import capps.jotpad.room.NotePriority
import capps.jotpad.room.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class EditFragVM(
    val editing: Boolean, private val note: Note?, private val repository: NoteRepository
) : ViewModel() {
    val title = MutableLiveData("")
    val body = MutableLiveData("")

    private val _colorCode = MutableLiveData(Event(""))
    val colorCode: LiveData<Event<String>> get() = _colorCode

    private val _statusMessage = MutableLiveData<Event<String>>()
    val statusMessage: LiveData<Event<String>> get() = _statusMessage

    private val _openBottomSheet = MutableLiveData<Event<Boolean>>()
    val openBottomSheet: LiveData<Event<Boolean>> get() = _openBottomSheet

    private val _closeBottomSheet = MutableLiveData<Event<Boolean>>()
    val closeBottomSheet: LiveData<Event<Boolean>> get() = _closeBottomSheet

    val fabShouldShow = MutableLiveData(true)

    private val _backButtonClicked = MutableLiveData<Event<Boolean>>()
    val backButtonClicked: LiveData<Event<Boolean>> get() = _backButtonClicked

    init {
        if (editing) {
            title.value = note!!.title
            body.value = note.body
            _colorCode.value = Event(note.color)
        } else {
            _colorCode.value = Event(Object.getRandomColorCode())
        }
    }

    fun save() {
        if (body.value!!.isBlank() && title.value!!.isBlank()) {
            toast("Empty note!")
        } else {
            val currentTimeInMillis = Calendar.getInstance().timeInMillis
            if (!editing) {
                insertNote(
                    Note(
                        currentTimeInMillis,
                        currentTimeInMillis,
                        title.value!!,
                        body.value!!,
                        colorCode.value!!.peekContent(),
                        NotePriority.LOW
                    )
                )
            } else {
                updateNote(
                    Note(
                        note!!.time,
                        currentTimeInMillis,
                        title.value!!,
                        body.value!!,
                        colorCode.value!!.peekContent(),
                        note.priority
                    )
                )
            }
        }
    }

    private fun toast(message: String) {
        _statusMessage.value = Event(message)
    }

    private fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val newRowID = repository.insert(note)

            withContext(Dispatchers.Main) {
                if (newRowID > -1) {
                    toast("Note Saved")
                } else {
                    toast("We're Sorry, Your Note Was Not Saved")
                }
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            val numberOfRows = repository.update(note)

            withContext(Dispatchers.Main) {
                if (numberOfRows > 0) {
                    toast("Note Updated")
                } else {
                    toast("We're Sorry, Your Note Was Not Updated")
                }
            }
        }
    }

    fun colorReturned(hex: String) {
        _colorCode.value = Event(hex)
    }

    fun openBottomSheet() {
        _openBottomSheet.value = Event(true)
    }

    fun closeBottomSheet() {
        _closeBottomSheet.value = Event(true)
    }

    fun showFAB() {
        if (fabShouldShow.value != true) {
            fabShouldShow.value = true
        }
    }

    fun hideFAB() {
        if (fabShouldShow.value != false) {
            fabShouldShow.value = false
        }
    }

    fun exit() {
        _backButtonClicked.value = Event(true)
    }
}