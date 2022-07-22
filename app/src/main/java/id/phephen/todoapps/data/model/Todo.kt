package id.phephen.todoapps.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Phephen on 23/07/2022.
 */

@Entity(tableName = "todo_table")
@Parcelize
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "todo_title")
    var title: String,
    @ColumnInfo(name = "todo_desc")
    var description: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "color")
    var color: String,
    @ColumnInfo(name = "important")
    var important: Boolean = false
) : Parcelable
