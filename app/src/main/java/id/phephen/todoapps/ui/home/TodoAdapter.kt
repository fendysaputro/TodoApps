package id.phephen.todoapps.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.phephen.todoapps.databinding.TodoItemBinding

/**
 * Created by Phephen on 23/07/2022.
 */
class TodoAdapter(private val viewModel: HomeViewModel): RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: TodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.MyViewHolder {
        return MyViewHolder(TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TodoAdapter.MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}