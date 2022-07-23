package id.phephen.todoapps.ui.detail

import android.os.Bundle
import android.text.Selection.setSelection
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.phephen.todoapps.R
import id.phephen.todoapps.data.model.ColorList
import id.phephen.todoapps.data.model.Colors
import id.phephen.todoapps.databinding.FragmentDetailToDoBinding
import id.phephen.todoapps.util.ColorObject
import id.phephen.todoapps.util.exhaustive

@AndroidEntryPoint
class DetailToDoFragment : Fragment(R.layout.fragment_detail_to_do) {

    private val viewModel: DetailToDoViewModel by viewModels()
    lateinit var selectedColor: ColorObject
    lateinit var binding: FragmentDetailToDoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailToDoBinding.bind(view)

        binding.apply {
            etTitle.setText(viewModel.todoTitle)
            etDesc.setText(viewModel.todoDesc)
            checkBoxImportant.isChecked = viewModel.todoImportance == true
            checkBoxImportant.jumpDrawablesToCurrentState()

            etTitle.addTextChangedListener {
                viewModel.todoTitle = it.toString()
            }

            etDesc.addTextChangedListener {
                viewModel.todoDesc = it.toString()
            }

            checkBoxImportant.setOnCheckedChangeListener { _, isChecked ->
                viewModel.todoImportance = isChecked
            }

            fabSaveTasks.setOnClickListener {
                viewModel.OnSaveClicked()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.detailTodoEvent.collect { event ->
                when (event) {
                    is DetailToDoViewModel.DetailTodoEvent.NavigateBackWithResult -> {
                        binding.etTitle.clearFocus()
                        setFragmentResult(
                            "add_edit_request",
                            bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    is DetailToDoViewModel.DetailTodoEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }

        loadColorSpinner()

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
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

}