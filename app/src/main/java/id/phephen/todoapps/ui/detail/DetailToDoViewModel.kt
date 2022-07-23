package id.phephen.todoapps.ui.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.phephen.todoapps.data.db.TodoDao
import id.phephen.todoapps.data.model.Todo
import id.phephen.todoapps.ui.ADD_TASK_RESULT_OK
import id.phephen.todoapps.ui.EDIT_TASK_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Created by Phephen on 23/07/2022.
 */
class DetailToDoViewModel @ViewModelInject constructor(
    private val todoDao: TodoDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    val todo = state.get<Todo>("todo")

    var todoTitle = state.get<String>("todoTitle") ?: todo?.title ?: ""
        set(value) {
            field = value
            state.set("todoTitle", value)
        }

    var todoDesc = state.get<String>("todoDesc") ?: todo?.description ?: ""
        set(value) {
            field = value
            state.set("todoDesc", value)
        }

    var todoColor = state.get<String>("todoColor") ?: todo?.color ?: ""
        set(value) {
            field = value
            state.set("todoColor", value)
        }

    var todoImportance = state.get<Boolean>("todoImportance") ?: todo?.important
        set(value) {
            field = value
            state.set("todoImportance", value)
        }

    private val detailTodoEventChannel = Channel<DetailTodoEvent>()
    val detailTodoEvent = detailTodoEventChannel.receiveAsFlow()

    fun OnSaveClicked() {
        if (todoTitle.isEmpty()) {
            showInvalidInputMessage("Title cannot be empty")
            return
        }
        if (todo != null) {
            val updateTodo = todo.copy(title = todoTitle, important = todoImportance)
            updateTodo(updateTodo)
        } else {
            val newTodo = Todo(title = todoTitle, description = todoDesc, color = todoColor, important = todoImportance)
            createTodo(newTodo)
        }
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        detailTodoEventChannel.send(DetailTodoEvent.ShowInvalidInputMessage(text))
    }

    private fun createTodo(newTodo: Todo) = viewModelScope.launch {
        todoDao.insertTodo(newTodo)
        detailTodoEventChannel.send(DetailTodoEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }

    private fun updateTodo(updateTodo: Todo) = viewModelScope.launch {
        todoDao.updateTodo(updateTodo)
        detailTodoEventChannel.send(DetailTodoEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }

    sealed class DetailTodoEvent {
        data class ShowInvalidInputMessage(val msg: String) : DetailTodoEvent()
        data class NavigateBackWithResult(val result: Int) : DetailTodoEvent()
    }
}