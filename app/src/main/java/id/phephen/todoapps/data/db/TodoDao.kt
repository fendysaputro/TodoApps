package id.phephen.todoapps.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import id.phephen.todoapps.data.model.Todo

/**
 * Created by Phephen on 23/07/2022.
 */

@Dao
interface TodoDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTodo(todo : Todo)

//    @Update()
//    suspend fun updateTodo(todo: Todo)

    @Query("SELECT * FROM todo_table WHERE todo_title LIKE :query")
    fun searchProduct(query: String): LiveData<List<Todo>>

}