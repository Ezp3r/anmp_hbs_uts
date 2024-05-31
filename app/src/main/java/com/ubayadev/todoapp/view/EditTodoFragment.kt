package com.ubayadev.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ubayadev.todoapp.R
import com.ubayadev.todoapp.databinding.FragmentCreateTodoBinding
import com.ubayadev.todoapp.databinding.FragmentEditTodoBinding
import com.ubayadev.todoapp.model.Todo
import com.ubayadev.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment(), RadioClickListener, TodoEditClickListener {
    private lateinit var binding:FragmentEditTodoBinding
    private lateinit var viewModel:DetailTodoViewModel
    //todo di isi saat observe tuntas di bagian observe
    private lateinit var todo:Todo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        binding.txtHeading.text= "Edit Todo"

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)

//        binding.btnSubmit.setOnClickListener {
//            todo.title = binding.txtTitle.text.toString()
//            todo.notes = binding.txtTitle.text.toString()
//            val radio = view.findViewById<RadioButton>(
//                binding.radioGroupPriority.checkedRadioButtonId)
//            todo.priority = radio.tag.toString().toInt()
//            viewModel.update(todo)
//            Toast.makeText(context, "Todo updated", Toast.LENGTH_SHORT).show()
//        }\

        binding.radioListener = this
        binding.submitListener = this

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
//            todo = it
//            binding.txtTitle.setText(it.title)
//            binding.txtNotes.setText(it.notes)
//            when (it.priority) {
//                3 -> binding.radioHigh.isChecked = true
//                2 -> binding.radioMedium.isChecked = true
//                1 -> binding.radioLow.isChecked = true
//            }
            binding.todo = it
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTodoBinding.inflate(inflater, container,
            false)
        return binding.root
    }

    override fun onRadioClick(v: View) {
        binding.todo!!.priority = v.tag.toString().toInt()
    }

    override fun onTodoEditClick(v: View) {
        viewModel.update(binding.todo!!)
        Toast.makeText(context, "Todo updated", Toast.LENGTH_SHORT).show()
    }
}