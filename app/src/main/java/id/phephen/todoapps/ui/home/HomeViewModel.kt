package id.phephen.todoapps.ui.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import id.phephen.todoapps.data.db.PrefManager
import id.phephen.todoapps.data.db.TodoDao
import id.phephen.todoapps.data.model.Todo
import id.phephen.todoapps.repository.TodoRepository
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 * Created by Phephen on 23/07/2022.
 */

class HomeViewModel @ViewModelInject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    fun insertTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.insertTodo(todo)
    }

    fun updateTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.updateTodo(todo)
    }

    fun getAllTodo() : LiveData<List<Todo>>{
        return todoRepository.getAllTodo()
    }

}