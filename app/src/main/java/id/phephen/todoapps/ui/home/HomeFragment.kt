package id.phephen.todoapps.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.hilt.android.AndroidEntryPoint
import id.phephen.todoapps.R
import id.phephen.todoapps.databinding.FragmentHomeBinding
import id.phephen.todoapps.ui.MainActivity
import id.phephen.todoapps.util.onQueryTextChange

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllTodo().observe(viewLifecycleOwner) {
            Log.e("todoList", it.toString())
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_menu_home, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChange {

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

    private fun setupViewModels() {
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

}