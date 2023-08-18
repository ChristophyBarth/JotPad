package capps.jotpad.viewmodel

import androidx.lifecycle.ViewModel
import capps.jotpad.room.Note
import capps.jotpad.room.NoteRepository

class HomeFragVM(private val repository: NoteRepository) : ViewModel() {
    val notes = mutableListOf<Note>()

    init {
        notes.add(Note(1691708400000, "Lorem", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. At erat pellentesque adipiscing commodo. Nullam non nisi est sit amet. Tempus imperdiet nulla malesuada pellentesque elit eget gravida. Nulla aliquet enim tortor at auctor urna nunc id. Magna fermentum iaculis eu non diam phasellus vestibulum lorem. At imperdiet dui accumsan sit amet. Lorem donec massa sapien faucibus et molestie ac feugiat sed. At varius vel pharetra vel turpis nunc eget. Amet justo donec enim diam vulputate. Aliquet nibh praesent tristique magna sit amet purus. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. At erat pellentesque adipiscing commodo. Nullam non nisi est sit amet. Tempus imperdiet nulla malesuada pellentesque elit eget gravida. Nulla aliquet enim tortor at auctor urna nunc id. Magna fermentum iaculis eu non diam phasellus vestibulum lorem. At imperdiet dui accumsan sit amet. Lorem donec massa sapien faucibus et molestie ac feugiat sed. At varius vel pharetra vel turpis nunc eget. Amet justo donec enim diam vulputate. Aliquet nibh praesent tristique magna sit amet purus.", "#FF60BDBD"))
        notes.add(Note(1691708400000, "Dolor", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut sem viverra aliquet eget sit amet. Aliquet eget sit amet tellus cras adipiscing enim eu turpis. Amet est placerat in egestas erat imperdiet. Augue interdum velit euismod in pellentesque massa placerat duis ultricies.", "#5C3D95"))
        notes.add(Note(1691708400000, "Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut sem viverra aliquet eget sit amet. Aliquet eget sit amet tellus cras adipiscing enim eu turpis. Amet est placerat in egestas erat imperdiet. Augue interdum velit euismod in pellentesque massa placerat duis ultricies.", "#FFEAB984"))
        notes.add(Note(1691708400000, "", "consectetur adipiscing elit, sed do eiusmod tempor incididunt", "#E389B9"))
        notes.add(Note(1691708400000, "Lorem", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut sem viverra aliquet eget sit amet. Aliquet eget sit amet tellus cras adipiscing enim eu turpis. Amet est placerat in egestas erat imperdiet. Augue interdum velit euismod in pellentesque massa placerat duis ultricies.", "#FFEAB984"))
    }


}