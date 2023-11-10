package capps.jotpad.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_data_table")
open class Note(

    @PrimaryKey @ColumnInfo(name = "note_id") val time: Long,

    @ColumnInfo(name = "note_time") val lastUpdated: Long,

    @ColumnInfo(name = "note_title") var title: String,

    @ColumnInfo(name = "note_body") var body: String,

    @ColumnInfo(name = "note_color") var color: String,

    @ColumnInfo(name = "note_priority") var priority: NotePriority,
)

enum class NotePriority(val colorCode: String) {
    LOW("#00FF00"), Medium("#FFFF00"), High("#FF0000")
}