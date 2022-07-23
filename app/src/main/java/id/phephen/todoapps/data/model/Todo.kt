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
    var title: String? = null,
    var description: String? = null,
    var color: String? = null,
    var important: Boolean? = null,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
) : Parcelable
