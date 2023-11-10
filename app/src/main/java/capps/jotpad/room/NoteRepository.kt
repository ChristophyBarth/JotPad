package capps.jotpad.room

open class NoteRepository(private val dao: NoteDAO) {
    val notes = dao.getAllNotes()

    suspend fun insert(note: Note): Long {
        return dao.insertNote(note)
    }

    suspend fun update(note: Note): Int {
        return dao.updateNote(note)
    }
}