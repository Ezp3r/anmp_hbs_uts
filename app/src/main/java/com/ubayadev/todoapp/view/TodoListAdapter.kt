package com.ubayadev.todoapp.view

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubayadev.todoapp.databinding.TodoItemLayoutBinding
import com.ubayadev.todoapp.model.Todo
import androidx.core.text.HtmlCompat


class TodoListAdapter(val todoList:ArrayList<Todo>,
                      val adapterOnClick: (Todo) -> Unit )
    : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>()
    , TodoCheckedChangeListener, TodoEditClickListener {
    class TodoViewHolder(var binding: TodoItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TodoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int)
    {
//        holder.binding.checkTask.text = todoList[position].title
//        holder.binding.checkTask.isChecked = false
//        holder.binding.checkTask.setOnCheckedChangeListener { compoundButton, b ->
//            if(compoundButton.isPressed) {
//                adapterOnClick(todoList[position])
//            }
//        }
//
//        holder.binding.imgEdit.setOnClickListener {
//            val action = TodoListFragmentDirections.actionEditTodo(
//                todoList[position].uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
        holder.binding.todo = todoList[position]
//        holder.binding.checkTask.isChecked = false
        holder.binding.listener = this
        holder.binding.editlistener = this

        val todo = todoList[position]
        holder.binding.todo = todo

        if (todo.isDone == 1) {
            holder.binding.checkTask.paintFlags =
                holder.binding.checkTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.binding.checkTask.paintFlags =
                holder.binding.checkTask.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateTodoList(newTodoList:List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onTodoCheckedChange(cb: CompoundButton, isChecked: Boolean, todo: Todo) {
        if (cb.isPressed) {
            adapterOnClick(todo)
        }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionEditTodo(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}