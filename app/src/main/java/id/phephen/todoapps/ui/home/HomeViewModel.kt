package id.phephen.todoapps.ui.home

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import id.phephen.todoapps.data.db.PrefManager
import id.phephen.todoapps.data.db.TodoDao
import id.phephen.todoapps.data.model.Todo
import id.phephen.todoapps.repository.TodoRepository
import id.phephen.todoapps.ui.ADD_TASK_RESULT_OK
import id.phephen.todoapps.ui.EDIT_TASK_RESULT_OK
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Created by Phephen on 23/07/2022.
 */

class HomeViewModel @ViewModelInject constructor(
    private val todoDao: TodoDao,
    private val prefManager: PrefManager,
    @Assisted private val state : SavedStateHandle
//    private val todoRepository: TodoRepository
) : ViewModel() {

    val todoData = state.get<Todo>("todo")

    val searchQuery = state.getLiveData("searchQuery", "")
    val prefFlow = prefManager.prefFlow

    val important = state.getLiveData("important", false)
    val filterFlow = prefManager.filterFlow

    val colorName = state.getLiveData("colorName", "")
    val filterColorFlow = prefManager.filterColorFlow

    private val todoEventChannel = Channel<TodoEvent>()
    val todosEvent = todoEventChannel.receiveAsFlow()

    private val todoFlow = combine(
        searchQuery.asFlow(),
        prefFlow
    ) { query, filterPref ->
        Pair(query, filterPref)
    }.flatMapLatest { (query, filterPref) ->
        todoDao.getTodo(query, filterPref.sortOrder)
    }

    val todo = todoFlow.asLiveData()

    private val filterFlowData = combine(
        important.asFlow(),
        filterFlow
    ) { import, fillt ->
        Pair(import, fillt)
    }.flatMapLatest { (import, fillt) ->
        todoDao.getTodoByImportant(import, fillt.sortOrder)
    }

    val todoFilter = filterFlowData.asLiveData()

    private val filterColorNameData = combine(
        colorName.asFlow(),
        filterColorFlow
    ) { colorName, fillCol ->
        Pair(colorName, fillCol)
    }.flatMapLatest { (colorName, fillCol) ->
        todoDao.getTodoListByColor(colorName)
    }

    val todoColorFilter = filterColorNameData.asLiveData()

    var todoColor = state.get<String>("todoColor") ?: todoData?.color ?: ""
        set(value) {
            field = value
            state.set("todoColor", value)
        }

    var todoColorName = state.get<String>("todoColorName") ?: todoData?.colorName ?: ""
        set(value) {
            field = value
            state.set("todoColorName", value)
        }

    fun onAddNewTodoClick() = viewModelScope.launch {
        todoEventChannel.send(TodoEvent.NavigateToAddTodoScreen)
    }

    fun onTodoSelected(todo: Todo) = viewModelScope.launch {
        todoEventChannel.send(TodoEvent.NavigateToEditTodoScreen(todo))
    }


    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_TASK_RESULT_OK -> showTodoSavedConfirmationMessage("Added")
            EDIT_TASK_RESULT_OK -> showTodoSavedConfirmationMessage("Updated")
        }
    }

    private fun showTodoSavedConfirmationMessage(text: String) = viewModelScope.launch {
        todoEventChannel.send(TodoEvent.ShowTodoSavedConfirmationMessage(text))
    }

    sealed class TodoEvent {
        object NavigateToAddTodoScreen : TodoEvent()
        data class NavigateToEditTodoScreen(val todo: Todo) : TodoEvent()
        data class ShowTodoSavedConfirmationMessage(val msg : String) : TodoEvent()
    }
}