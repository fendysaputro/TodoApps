package id.phephen.todoapps.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import id.phephen.todoapps.data.model.Todo
import id.phephen.todoapps.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Phephen on 23/07/2022.
 */

@Database(entities = [Todo::class], version = 6)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun getTodoDao(): TodoDao

    class CallBackDb @Inject constructor(
        private val database: Provider<TodoDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().getTodoDao()

            applicationScope.launch {
                dao.insertTodo(Todo("Create db", "description",  "#FFBB86FC", important = true))
                dao.insertTodo(Todo("Create retrofit", "description",  "#FF6200EE"))
                dao.insertTodo(Todo("Create mvvm", "description",  "#FF03DAC5", important = true))
                dao.insertTodo(Todo("Create di", "description",  "#FF018786"))
            }

        }
    }
}