package id.phephen.todoapps.ui.home

import android.os.Bundle
import android.text.Selection.setSelection
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.Toast
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
import id.phephen.todoapps.data.model.ColorList
import id.phephen.todoapps.data.model.Todo
import id.phephen.todoapps.databinding.FragmentHomeBinding
import id.phephen.todoapps.ui.detail.ColorSpinnerAdapter
import id.phephen.todoapps.util.ColorObject
import id.phephen.todoapps.util.exhaustive
import id.phephen.todoapps.util.onQueryTextChange
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.fab_add_note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), TodoAdapter.OnItemClickListener {
    private val viewModel: HomeViewModel by viewModels()
    private val todoAdapter = TodoAdapter(this)
    lateinit var selectedColor: ColorObject
    lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        binding.apply {
            rvTodo.apply {
                adapter = todoAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

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

        fab_add_note.setOnClickListener {
            viewModel.onAddNewTodoClick()
        }

        loadColorSpinner()

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
            R.id.action_filter_by_important -> {
                item.isChecked = !item.isChecked
                if (item.isChecked) {
                    viewModel.important.value = item.isChecked
                    viewModel.todoFilter.observe(viewLifecycleOwner) {
                        todoAdapter.submitList(it)
                    }
                } else {
                    viewModel.todo.observe(viewLifecycleOwner) {
                        todoAdapter.submitList(it)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onItemClick(todo: Todo) {
        viewModel.onTodoSelected(todo)
    }

    private fun loadColorSpinner() {
        selectedColor = ColorList().defaultColor
        binding.colorSpinner.apply {
            adapter = ColorSpinnerAdapter(requireContext(), ColorList().basicColors())
            setSelection(ColorList().colorPosition(selectedColor), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    selectedColor = ColorList().basicColors()[position]
                    viewModel.todoColor = selectedColor.hexHash
                    viewModel.todoColorName = selectedColor.name
                    viewModel.colorName.value = selectedColor.name
                    viewModel.todoColorFilter.observe(viewLifecycleOwner) {
                        todoAdapter.submitList(it)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }
}