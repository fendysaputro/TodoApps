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

@Database(entities = [Todo::class], version = 1)
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
                dao.insertTodo(Todo(1, "Create db", "description", "content", "#FFFFFFF"))
                dao.insertTodo(Todo(2, "Create retrofit", "description", "content", "#00FFFFFFF"))
                dao.insertTodo(Todo(3, "Create mvvm", "description", "content", "#FFFFFFF"))
                dao.insertTodo(Todo(4, "Create di", "description", "content", "#FFFFFFF"))
            }

        }
    }

//    companion object {
//        @Volatile
//        private var instance: TodoDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            instance ?: createDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun createDatabase(context: Context) = Room.databaseBuilder(
//            context.applicationContext,
//            TodoDatabase::class.java,
//            "todo_db"
//        ).build()
//
//    }

}