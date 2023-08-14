package capps.jotpad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import capps.jotpad.others.Event
import capps.jotpad.others.Object
import capps.jotpad.room.Note
import capps.jotpad.room.NoteRepository
import java.util.Calendar

class EditFragVM(val editing: Boolean, private val note: Note?, private val repository: NoteRepository): ViewModel() {
    val title = MutableLiveData("")
    val body = MutableLiveData("")

    private val _colorCode = MutableLiveData("")
    val colorCode : LiveData<String> get() = _colorCode

    private val _statusMessage = MutableLiveData<Event<String>>()
    val statusMessage : LiveData<Event<String>> get() = _statusMessage

    private val _openBottomSheet = MutableLiveData<Event<Boolean>>()
    val openBottomSheet : LiveData<Event<Boolean>> get() = _openBottomSheet

    private val _closeBottomSheet = MutableLiveData<Event<Boolean>>()
    val closeBottomSheet : LiveData<Event<Boolean>> get() = _closeBottomSheet

    private val _backButton = MutableLiveData<Event<Boolean>>()
    val backButton : LiveData<Event<Boolean>> get() = _backButton

    init {
        if (editing){
            title.value = note!!.title
            body.value = note.body
            _colorCode.value = note.color
        }else{
            _colorCode.value = Object.getRandomColorCode()
        }
    }

    fun save(){
        if (body.value!!.isBlank() && title.value!!.isBlank()){
            toast("Empty note!")
        }else{
            if (!editing){
                insertNote(Note(Calendar.getInstance().timeInMillis, title.value!!, body.value!!, colorCode.value!!))
            }else{
                updateNote(Note(note!!.time, title.value!!, body.value!!, colorCode.value!!))
            }
        }
    }

    private fun toast(message: String) {
        _statusMessage.value = Event(message)
    }

    private fun insertNote(note: Note) {

    }

    private fun updateNote(note: Note) {

    }

    fun colorReturned(hex: String){
        _colorCode.value = hex
    }

    fun openBottomSheet(){
        _openBottomSheet.value = Event(true)
    }

    fun closeBottomSheet(){
        _closeBottomSheet.value = Event(true)
    }

    fun exit(){
        _backButton.value = Event(true)
    }
}