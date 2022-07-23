package id.phephen.todoapps.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import id.phephen.todoapps.data.model.Todo
import kotlinx.coroutines.flow.Flow

/**
 * Created by Phephen on 23/07/2022.
 */

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    fun getTodo(query: String, sortOrder: SortOrder): Flow<List<Todo>> =
        getTodoSortByTitle(query)

    @Update()
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * FROM todo_table")
    fun getAllTodo(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE title LIKE '%' || :searchQuery || '%' ORDER BY important DESC, title")
    fun getTodoSortByTitle(searchQuery: String): Flow<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE title LIKE :query")
    fun searchItem(query: String): LiveData<List<Todo>>

}