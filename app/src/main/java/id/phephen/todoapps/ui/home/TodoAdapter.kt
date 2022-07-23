package id.phephen.todoapps.ui.home

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.phephen.todoapps.data.model.Todo
import id.phephen.todoapps.databinding.TodoItemBinding

/**
 * Created by Phephen on 23/07/2022.
 */
class TodoAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Todo, TodoAdapter.MyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTodoItem = getItem(position)
        holder.bind(currentTodoItem)
    }

    inner class MyViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val todo = getItem(position)
                        listener.onItemClick(todo)
                    }
                }
            }
        }
        fun bind(todo: Todo) {
            binding.apply {
                root.setBackgroundColor(Color.parseColor(todo.color))
                todoItemTitle.text = todo.title
                todoItemDesc.text = todo.description
                ivImportant.isVisible = todo.important == true
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(todo: Todo)
    }

    class DiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Todo, newItem: Todo) =
            oldItem == newItem

    }

}