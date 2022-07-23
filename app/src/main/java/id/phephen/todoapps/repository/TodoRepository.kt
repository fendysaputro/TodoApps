package id.phephen.todoapps.repository

import androidx.lifecycle.LiveData
import id.phephen.todoapps.data.db.TodoDatabase
import id.phephen.todoapps.data.model.Todo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Phephen on 23/07/2022.
 */

@Singleton
class TodoRepository @Inject constructor(val db: TodoDatabase) {

    suspend fun insertTodo(todo : Todo) {
        db.getTodoDao().insertTodo(todo)
    }

    suspend fun updateTodo(todo: Todo) {
        db.getTodoDao().updateTodo(todo)
    }

    fun getAllTodo() : LiveData<List<Todo>> {
        return db.getTodoDao().getAllTodo()
    }

}