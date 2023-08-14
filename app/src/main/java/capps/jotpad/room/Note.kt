package capps.jotpad.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_data_table")
data class Note(

    @PrimaryKey
    @ColumnInfo(name = "note_id")
    val time: Long,

    @ColumnInfo(name = "note_title")
    var title: String,

    @ColumnInfo(name = "note_body")
    var body: String,

    @ColumnInfo(name = "note_color")
    var color: String
)