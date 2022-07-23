package id.phephen.todoapps.data.db

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import id.phephen.todoapps.di.ApplicationScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Phephen on 23/07/2022.
 */

enum class SortOrder { BY_NAME }
data class FilterPref(val sortOrder: SortOrder)

@Singleton
class PrefManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore("user_pref")

    val prefFlow = dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }
        .map { preeef ->
            val sortOrder = SortOrder.valueOf(
                preeef[PreferencesKeys.SORT_ORDER] ?: ""
            )

            FilterPref(sortOrder)
        }

    suspend fun updateSortOrder(sortOrder: SortOrder){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_ORDER] = sortOrder.name
        }
    }


    private object PreferencesKeys {
        val SORT_ORDER = preferencesKey<String>("sort_order")
    }

}