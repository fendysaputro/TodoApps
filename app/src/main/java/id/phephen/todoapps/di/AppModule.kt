package id.phephen.todoapps.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.phephen.todoapps.ToDoApplication
import id.phephen.todoapps.data.db.TodoDatabase
import id.phephen.todoapps.data.network.Networking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by Phephen on 23/07/2022.
 */

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDb(
        application: Application,
        callback : TodoDatabase.CallBackDb
    ) =
        Room.databaseBuilder(application, TodoDatabase::class.java, "todo_db")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    fun provideTodoDao(db: TodoDatabase) = db.getTodoDao()


    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Networking.create(
            Networking.BASE_URL,
            10 * 1024 * 1024,
        )
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope