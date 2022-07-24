package id.phephen.todoapps.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import id.phephen.todoapps.data.model.Todo
import kotlinx.coroutines.flow.Flow
import java.nio.channels.FileLock

/**
 * Created by Phephen on 23/07/2022.
 */

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    fun getTodo(query: String, sortOrder: SortOrder): Flow<List<Todo>> =
        getTodoSortByTitle(query)

    fun getTodoByImportant(important: Boolean, sortOrder: SortOrder): Flow<List<Todo>> =
        getTodoFilterImportance(important)

    fun getDataByColor(colorName: String, sortOrder: SortOrder): Flow<List<Todo>> =
        getTodoListByColor(colorName)

    @Update()
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM todo_table")
    fun getAllTodo(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE title LIKE '%' || :searchQuery || '%' ORDER BY important DESC, title")
    fun getTodoSortByTitle(searchQuery: String): Flow<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE important = :important ORDER BY title ASC")
    fun getTodoFilterImportance(important: Boolean): Flow<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE colorName = :colorName ORDER BY title ASC")
    fun getTodoListByColor(colorName: String): Flow<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE title LIKE :query")
    fun searchItem(query: String): LiveData<List<Todo>>

}