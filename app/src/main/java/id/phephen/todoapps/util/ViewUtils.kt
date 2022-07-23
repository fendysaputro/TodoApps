package id.phephen.todoapps.util

import androidx.appcompat.widget.SearchView

/**
 * Created by Phephen on 23/07/2022.
 */

inline fun SearchView.onQueryTextChange(crossinline listener : (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }

    })
}