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

private const val TAG = "PrefManager"
enum class SortOrder { BY_NAME }
data class FilterPref(val sortOrder: SortOrder, val showImportant: Boolean, val colorName: String)

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
                preeef[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_NAME.name
            )
            val showImportant = preeef[PreferencesKeys.IMPORTANT] ?: false

            val colorName = preeef[PreferencesKeys.COLOR_NAME] ?: "White"

            FilterPref(sortOrder, showImportant, colorName)
        }

    val filterFlow = dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { filterPrefff ->
            val sortOrder = SortOrder.valueOf(
                filterPrefff[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_NAME.name
            )
            val filterBy = filterPrefff[PreferencesKeys.IMPORTANT] ?: false
            val colorName = filterPrefff[PreferencesKeys.COLOR_NAME] ?: "White"
            FilterPref(sortOrder, filterBy, colorName)
        }

    val filterColorFlow = dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { filterColorPrefff ->
            val sortOrder = SortOrder.valueOf(
                filterColorPrefff[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_NAME.name
            )
            val filterBy = filterColorPrefff[PreferencesKeys.IMPORTANT] ?: false
            val colorName = filterColorPrefff[PreferencesKeys.COLOR_NAME] ?: "White"
            FilterPref(sortOrder, filterBy, colorName)
        }

    private object PreferencesKeys {
        val SORT_ORDER = preferencesKey<String>("sort_order")
        val IMPORTANT = preferencesKey<Boolean>("important")
        val COLOR_NAME = preferencesKey<String>("color_name")
    }

}