package capps.jotpad.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDAO {

    @Insert
    suspend fun insertNote(note: Note) : Long

    @Update
    suspend fun updateNote(note: Note) : Int

    @Delete
    suspend fun deleteNote(note: Note) : Int

    @Query("SELECT * FROM note_data_table")
    fun getAllNotes(): LiveData<MutableList<Note>>
}