package id.phephen.todoapps.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.phephen.todoapps.R
import id.phephen.todoapps.data.model.Todo
import id.phephen.todoapps.databinding.FragmentHomeBinding
import id.phephen.todoapps.util.exhaustive
import id.phephen.todoapps.util.onQueryTextChange
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), TodoAdapter.OnItemClickListener {
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        val todoAdapter = TodoAdapter(this)
        binding.apply {
            rvTodo.apply {
                adapter = todoAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

//        fab_add_note.setOnClickListener {
//            viewModel.onAddNewTodoClick()
//        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        viewModel.todo.observe(viewLifecycleOwner) {
            todoAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.todosEvent.collect { event ->
                when (event) {
                    is HomeViewModel.TodoEvent.NavigateToAddTodoScreen -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToDetailTodoFragment(null, "New Todo")
                        findNavController().navigate(action)
                    }
                    is HomeViewModel.TodoEvent.NavigateToEditTodoScreen -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToDetailTodoFragment(event.todo, "Edit Todo")
                        findNavController().navigate(action)
                    }
                    is HomeViewModel.TodoEvent.ShowTodoSavedConfirmationMessage -> {
                        Snackbar.make(requireView(),event.msg, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }.exhaustive
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_menu_home, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChange {
            viewModel.searchQuery.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter_by_color -> {
                true
            }
            R.id.action_filter_by_important -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onItemClick(todo: Todo) {
        viewModel.onTodoSelected(todo)
    }
}