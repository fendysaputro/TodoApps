package id.phephen.todoapps.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.phephen.todoapps.ToDoApplication
import id.phephen.todoapps.data.db.TodoDao
import id.phephen.todoapps.data.db.TodoDatabase
import id.phephen.todoapps.data.network.Networking
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Phephen on 23/07/2022.
 */

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    lateinit var context: Context

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao =
        todoDatabase.getTodoDao()


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Networking.create(
            context,
            Networking.BASE_URL,
            context.cacheDir,
            10 * 1024 * 1024,
        )
}